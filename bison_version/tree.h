typedef enum { 
    PLUS_NODE,
    MINUS_NODE,
    MUL_NODE,
    DIV_NODE,
    VAL_NODE,
    ID_NODE
} op_type;

typedef struct node node;

void print_tree(const node *root);
node *new_value(int val);
node *new_op(op_type op, node *left, node *right);
node *new_id(const char *id);
void clear(node *n);

