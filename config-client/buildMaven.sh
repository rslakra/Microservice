#!/bin/bash
# Author: Rohtash Lakra
# Source common version function
set -e  # Exit on error

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
VERSION_SCRIPT="${SCRIPT_DIR}/../version.sh"

# Check if version.sh exists and source it
if [ ! -f "${VERSION_SCRIPT}" ]; then
    echo "Error: version.sh not found at ${VERSION_SCRIPT}"
    exit 1
fi

source "${VERSION_SCRIPT}"

# Verify buildVersion function is available
if ! type buildVersion &>/dev/null; then
    echo "Error: buildVersion function not found in version.sh"
    exit 1
fi

echo
#JAVA_VERSION=11
#export JAVA_HOME=$(/usr/libexec/java_home -v $JAVA_VERSION)
echo "JAVA_HOME: ${JAVA_HOME}"
echo

# Build versions using version.sh
SNAPSHOT_VERSION=$(buildVersion SNAPSHOT)
RELEASE_VERSION=$(buildVersion)

echo "SNAPSHOT_VERSION: ${SNAPSHOT_VERSION}"
echo "RELEASE_VERSION: ${RELEASE_VERSION}"
echo

# Build with SNAPSHOT version
echo "Building with SNAPSHOT version: ${SNAPSHOT_VERSION}"
mvn clean install -Drevision=$SNAPSHOT_VERSION

# Build with RELEASE version (skip tests)
echo "Building with RELEASE version: ${RELEASE_VERSION} (skipping tests)"
mvn install -Drevision=$RELEASE_VERSION -DskipTests=true

echo
echo "Build completed successfully!"
echo
