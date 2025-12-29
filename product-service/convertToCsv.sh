#!/bin/bash

# Script to convert various file formats to CSV format for product upload
# Usage: ./convertToCsv.sh <input_file> [output_file]
#
# Supported input formats:
# - Text files with tab-separated values
# - Text files with pipe-separated values
# - JSON files (basic support)
# - Existing CSV files (will validate and format)

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if input file is provided
if [ $# -lt 1 ]; then
    echo -e "${RED}Error: Input file is required${NC}"
    echo "Usage: $0 <input_file> [output_file]"
    echo ""
    echo "Example:"
    echo "  $0 products.txt products.csv"
    echo "  $0 products.json products.csv"
    exit 1
fi

INPUT_FILE="$1"
OUTPUT_FILE="${2:-products.csv}"

# Check if input file exists
if [ ! -f "$INPUT_FILE" ]; then
    echo -e "${RED}Error: Input file '$INPUT_FILE' does not exist${NC}"
    exit 1
fi

# Get file extension (convert to lowercase for comparison)
EXTENSION="${INPUT_FILE##*.}"
EXTENSION_LOWER=$(echo "$EXTENSION" | tr '[:upper:]' '[:lower:]')

echo -e "${GREEN}Converting $INPUT_FILE to $OUTPUT_FILE...${NC}"

# CSV Header
HEADER="name,brand,description,isbn,sku,image_url"

# Function to detect delimiter
detect_delimiter() {
    local file="$1"
    local first_line=$(head -n 1 "$file")
    
    if [[ "$first_line" == *$'\t'* ]]; then
        echo $'\t'
    elif [[ "$first_line" == *'|'* ]]; then
        echo '|'
    elif [[ "$first_line" == *','* ]]; then
        echo ','
    else
        echo ' '
    fi
}

# Process based on file type
case "$EXTENSION_LOWER" in
    csv)
        echo -e "${YELLOW}Validating and formatting CSV file...${NC}"
        # Check if header exists
        if ! head -n 1 "$INPUT_FILE" | grep -q "name"; then
            echo -e "${YELLOW}Adding CSV header...${NC}"
            echo "$HEADER" > "$OUTPUT_FILE"
            cat "$INPUT_FILE" >> "$OUTPUT_FILE"
        else
            cp "$INPUT_FILE" "$OUTPUT_FILE"
        fi
        ;;
    json)
        echo -e "${YELLOW}Converting JSON to CSV...${NC}"
        echo "$HEADER" > "$OUTPUT_FILE"
        # Basic JSON to CSV conversion (requires jq or python)
        if command -v jq &> /dev/null; then
            jq -r '.[] | [.name, .brand // "", .description // "", .isbn, .sku, .imageUrl // ""] | @csv' "$INPUT_FILE" >> "$OUTPUT_FILE"
        elif command -v python3 &> /dev/null; then
            python3 << EOF >> "$OUTPUT_FILE"
import json
import csv
import sys

with open('$INPUT_FILE', 'r') as f:
    data = json.load(f)
    if isinstance(data, list):
        for item in data:
            print(f"{item.get('name', '')},{item.get('brand', '')},{item.get('description', '')},{item.get('isbn', '')},{item.get('sku', '')},{item.get('imageUrl', '')}")
EOF
        else
            echo -e "${RED}Error: JSON conversion requires 'jq' or 'python3' to be installed${NC}"
            exit 1
        fi
        ;;
    txt|tsv)
        echo -e "${YELLOW}Converting text file to CSV...${NC}"
        DELIMITER=$(detect_delimiter "$INPUT_FILE")
        echo "$HEADER" > "$OUTPUT_FILE"
        
        # Skip header if it exists
        if head -n 1 "$INPUT_FILE" | grep -qi "name"; then
            tail -n +2 "$INPUT_FILE" | while IFS="$DELIMITER" read -r name brand description isbn sku image_url; do
                # Clean and quote fields if they contain commas
                name=$(echo "$name" | sed 's/,/ /g' | xargs)
                brand=$(echo "$brand" | sed 's/,/ /g' | xargs)
                description=$(echo "$description" | sed 's/,/ /g' | xargs)
                isbn=$(echo "$isbn" | sed 's/,/ /g' | xargs)
                sku=$(echo "$sku" | sed 's/,/ /g' | xargs)
                image_url=$(echo "$image_url" | sed 's/,/ /g' | xargs)
                echo "$name,$brand,$description,$isbn,$sku,$image_url" >> "$OUTPUT_FILE"
            done
        else
            cat "$INPUT_FILE" | while IFS="$DELIMITER" read -r name brand description isbn sku image_url; do
                name=$(echo "$name" | sed 's/,/ /g' | xargs)
                brand=$(echo "$brand" | sed 's/,/ /g' | xargs)
                description=$(echo "$description" | sed 's/,/ /g' | xargs)
                isbn=$(echo "$isbn" | sed 's/,/ /g' | xargs)
                sku=$(echo "$sku" | sed 's/,/ /g' | xargs)
                image_url=$(echo "$image_url" | sed 's/,/ /g' | xargs)
                echo "$name,$brand,$description,$isbn,$sku,$image_url" >> "$OUTPUT_FILE"
            done
        fi
        ;;
    *)
        echo -e "${YELLOW}Unknown file type. Attempting to detect format...${NC}"
        DELIMITER=$(detect_delimiter "$INPUT_FILE")
        echo "$HEADER" > "$OUTPUT_FILE"
        
        # Try to parse as delimited text
        tail -n +1 "$INPUT_FILE" | while IFS="$DELIMITER" read -r name brand description isbn sku image_url; do
            name=$(echo "$name" | sed 's/,/ /g' | xargs)
            brand=$(echo "$brand" | sed 's/,/ /g' | xargs)
            description=$(echo "$description" | sed 's/,/ /g' | xargs)
            isbn=$(echo "$isbn" | sed 's/,/ /g' | xargs)
            sku=$(echo "$sku" | sed 's/,/ /g' | xargs)
            image_url=$(echo "$image_url" | sed 's/,/ /g' | xargs)
            echo "$name,$brand,$description,$isbn,$sku,$image_url" >> "$OUTPUT_FILE"
        done
        ;;
esac

# Validate CSV format
if [ -f "$OUTPUT_FILE" ]; then
    LINE_COUNT=$(wc -l < "$OUTPUT_FILE" | xargs)
    if [ "$LINE_COUNT" -lt 2 ]; then
        echo -e "${YELLOW}Warning: CSV file has only header or is empty${NC}"
    else
        echo -e "${GREEN}Success! Created $OUTPUT_FILE with $((LINE_COUNT - 1)) product(s)${NC}"
        echo ""
        echo "CSV Format:"
        echo "  Header: $HEADER"
        echo "  Sample (first 3 lines):"
        head -n 3 "$OUTPUT_FILE" | sed 's/^/    /'
    fi
else
    echo -e "${RED}Error: Failed to create output file${NC}"
    exit 1
fi

