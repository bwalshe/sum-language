cmake_minimum_required(VERSION 3.10)

project(Calculator)

find_package(BISON)
BISON_TARGET(CalcParser calc.y ${CMAKE_CURRENT_BINARY_DIR}/calc_parser.c 
             DEFINES_FILE ${CMAKE_CURRENT_BINARY_DIR}/calc_parser.h)
find_package(FLEX)
FLEX_TARGET(CalcLexer calc.l ${CMAKE_CURRENT_BINARY_DIR}/calc_lexer.c)

add_executable(calc tree.c ${BISON_CalcParser_OUTPUTS} ${FLEX_CalcLexer_OUTPUTS})
include_directories("${PROJECT_SOURCE_DIR}")
target_link_libraries(calc fl)
