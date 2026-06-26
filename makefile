SRC_DIR := src

PARSER_DIR := PARSER
BIN_DIR := bin
MAIN_CLASS := game.Main

JAR_FILE := given.jar

SOURCES := $(shell find $(SRC_DIR) $(PARSER_DIR) -name "*.java")

CP := $(BIN_DIR):$(JAR_FILE)

# Commandes
all: compile

compile:
	@mkdir -p $(BIN_DIR)
	@javac -d $(BIN_DIR) -cp "$(CP)" $(SOURCES)

run: compile
	@java -cp "$(CP)" $(MAIN_CLASS)

clean:
	@rm -rf $(BIN_DIR)

.PHONY: all compile run clean