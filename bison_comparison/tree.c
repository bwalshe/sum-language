#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include "tree.h"


typedef struct children {
    node *left;
    node *right;
} children;


struct node {
    op_type op;
    union {
        int val;
        children children;
        char *id;
    };
}; 


void print_node(const node *node, int level);


void indent(int n)
{
    for(int i = 0; i < n - 1; ++i) 
    {
        printf(" | ");
    }
    if(n > 0)
    {
        printf(" |-");
    }
}


void print_op(op_type op, const children *operands, int level)
{
    indent(level);
    switch(op) 
    {
        case PLUS_NODE:  printf("(+)\n");
                    break;
        case MINUS_NODE: printf("(-)\n");
                    break;
        case MUL_NODE:   printf("(*)\n");
                    break; 
        case DIV_NODE:   printf("(/)\n");
                    break;
    } 
    print_node(operands->left, level + 1);
    print_node(operands->right, level + 1);
}   


void print_tree(const node *root)
{
    print_node(root, 0);
}


void print_node(const node *node, int level) 
{
    switch(node->op)
    {
        case VAL_NODE: 
            indent(level);
            printf("value: %d\n", node->val);
            break;
        case ID_NODE:
            indent(level);
            printf("id: %s\n", node->id);
            break; 
        default: print_op(node->op, &node->children, level);

    }
}


node *new_value(int val) 
{
    node *n  = malloc(sizeof(node));
    n->op = VAL_NODE;
    n->val = val;
    return n;
}

node *new_id(const char *id)
{
    node *n  = malloc(sizeof(node));
    n->op = ID_NODE;
    n->id = malloc(strlen(id) * sizeof(char) + 1);
    strcpy(n->id, id);
    return n;
}


node *new_op(op_type op, node *left, node *right) 
{
    node *n = malloc(sizeof(node));
    n->op = op;
    n->children.left = left;
    n->children.right=right;
    return n;
}

void clear(node *n)
{
    if(n->op != VAL_NODE && n->op != ID_NODE)
    {
        clear(n->children.left);
        clear(n->children.right);
    }
    
    if(n->op == ID_NODE)
    {
        free(n->id);
    }

    free(n);
}


