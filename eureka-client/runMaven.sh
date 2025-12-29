#!/bin/bash
# Author: Rohtash Lakra
clear
set -e  # Exit on error

# Source common version function
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
echo "JAVA_HOME: ${JAVA_HOME}"
echo

# Get RELEASE version using version.sh
RELEASE_VERSION=$(buildVersion)
echo "Running with RELEASE version: ${RELEASE_VERSION}"
echo

# Run the Spring Boot application
mvn clean spring-boot:run -Drevision=$RELEASE_VERSION

echo
