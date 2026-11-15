#!/bin/bash
#Author:Rohtash Lakra
#
# Syncs every submodule (recursively) to the latest code of its remote default branch.
#
# WARNING: this OVERRIDES local work in the submodules. Uncommitted changes, local commits that
# are not on the remote and untracked files (ignored files are kept) are discarded.
set -u

clear
cd "$(dirname "$0")" || exit 1
PROJECT_HOME=$PWD
echo
echo "Syncing [${PROJECT_HOME}] ..."
echo

# Step 1 - pick up url changes from .gitmodules and initialize submodules recursively
git submodule sync --recursive || exit 1
git submodule update --init --recursive --force || exit 1
echo

# Step 2 - in every submodule: fetch, check out the remote default branch and hard reset it to the
# remote head (this is what discards the local changes), then remove untracked files.
git submodule foreach --recursive '
  echo "Syncing [$displaypath] ..."
  git fetch --prune origin || exit 1

  # Default branch of the remote (origin/HEAD); fall back to master, then main.
  git remote set-head origin --auto >/dev/null 2>&1
  branch=$(git symbolic-ref --quiet --short refs/remotes/origin/HEAD 2>/dev/null | sed "s#^origin/##")
  if [ -z "$branch" ]; then
    if git show-ref --verify --quiet refs/remotes/origin/master; then
      branch=master
    else
      branch=main
    fi
  fi

  git checkout --force -B "$branch" "origin/$branch" || exit 1
  git reset --hard "origin/$branch" || exit 1
  git clean -ffd
  echo "[$displaypath] is now at $(git rev-parse --short HEAD) on [$branch]"
  echo
'
STATUS=$?

echo
if [ ${STATUS} -eq 0 ]; then
  echo "Sync completed."
else
  echo "Sync failed with exit code ${STATUS}." >&2
fi
exit ${STATUS}
