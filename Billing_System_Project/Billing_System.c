#include <stdio.h>
#include <string.h>

int check_available(product_input){
    FILE *fp = fopen("Product_details.txt", "r");
    int i = 0, j = 0;
    char buffer[200];
    rewind(fp);
    while (fgets(buffer, sizeof(buffer), fp) != NULL) {
        j++;
    }
    char list[j][200];
    char product_names[j][200];
    FILE *fp1 = fopen("Product_details.txt", "r");
    while (fgets(list[i], sizeof(list[i]), fp1) != NULL && i < j) {
        i++;
    }
    for (int k = 0; k < i; k++) {
        char product_name[100];
        int space_index = strcspn(list[k], " ");
        strncpy(product_name, list[k], space_index);
        product_name[space_index] = '\0';
        if(strncmp(product_name,product_input,4)==0){
            return 0;
        }
    }
    return 1;
}

void add_product(){
    input_item:{
        printf("\n\n\t\t  Enter the product details: ");
        char new_item[100];
        fflush(stdin);
        scanf("%[^\n]%*c",new_item);
        for (int i = 0; new_item[i] != '\0'; i++) {
            new_item[i] = tolower(new_item[i]);
        }
        char product_name[100];
        int space_index = strcspn(new_item, " ");
        strncpy(product_name, new_item, space_index);
        product_name[space_index] = '\0';
        int result = check_available(product_name);
        if(result==1){
            FILE *fp = fopen("Product_details.txt", "a");
            fprintf(fp, "%s\n", tolower(new_item));
            fclose(fp);
            get_user_choice();
        }
        else{
            printf("\n\n\t\t  Product already exsist\n");
            goto input_item;
        }
    }
}

char input_names[200][200];
int input_price[200];
int quantity_price[200];
int rear = -1, front = -1;
void price_enqueue(int max, int element){
     if (rear == max - 1) {
        return;
    }
    if (front == -1) {
        front = 0;
    }
    rear++;
    input_price[rear] = element;
}
int q = 0;
int a = 0;
void calculate_total(){
    char input_product[200];
    int quantity = 0;
    printf("\n\t  Enter the buyed product name(Enter exit to exit): ");
    fflush(stdin);
    scanf("%[^\n]%*c",input_product);
    if(strncmp(input_product,"exit",4)==0){
        get_user_choice();
    }
    else{
        printf("\n\t  Enter the quantity: ");
        fflush(stdin);
        scanf("%d",&quantity);
        quantity_price[q++] = quantity;
        for (int i = 0; input_product[i] != '\0'; i++) {
            input_product[i] = tolower(input_product[i]);
        }
        FILE *fp = fopen("Product_details.txt", "r");
        int j = 0;
        char buffer[200];
        rewind(fp);
        while (fgets(buffer, sizeof(buffer), fp) != NULL) {
            j++;
        }
        int i = 0;
        if(j!=0){
            char list[j][200];
            FILE *fp1 = fopen("Product_details.txt", "r");
            while (fgets(list[i], sizeof(list[i]), fp1) != NULL && i < j) {
                i++;
            }
            int not_found = 0;
            for (int k = 0; k < i; k++) {
                char product_name[100];
                int product_price;
                int space_index = strcspn(list[k], " ");
                strncpy(product_name, list[k], space_index);
                product_name[space_index] = '\0';
                product_price = atoi(list[k] + space_index + 1);
                if(strncmp(product_name,input_product,4)==0){
                    strcpy(input_names[a],input_product);
                    price_enqueue(i,product_price);
                    a++;
                    not_found = 0;
                    break;
                }
                else{
                    not_found = 1;
                }
            }
            if(not_found==1){
                printf("\n\t\tProduct not found");
            }
            calculate_total();
        }
        else{
            printf("\n\n\t\t  Please add atleast one product");
            add_product();
        }
    }
}

int temp = 0;
int total_price = 0;
int price_dequeue(){
    int element = input_price[temp];
    front++;
    total_price+=(element*quantity_price[temp]);
    temp++;
    return element;
}

void display_bill(){
    int t = 0;
    printf("\n\n\t\t****************************************************************************");
    printf("\n\n\t\t     Product name        Product Quantity               Product Prize       ");
    for(t=0;t < a;t++){
        printf("\n\n\t\t      %s                   %d                              %d             ",input_names[t],quantity_price[t],price_dequeue());
    }
    printf("\n\n\t\t****************************************************************************");
    printf("\n\n\t\t\t\t      Total Price: %d",total_price);
    printf("\n\n\t\t****************************************************************************");
    get_user_choice();
}

void get_user_choice(){
    int choice;
    printf("\n\n\t\t*********************************");
    printf("\n\t\t*        Billing System         *");
    printf("\n\t\t*                               *");
    printf("\n\t\t*     1. Add new product        *");
    printf("\n\t\t*    2. Insert Buyed Product    *");
    printf("\n\t\t*     3. Display Bill           *");
    printf("\n\t\t*          4. Exit              *");
    printf("\n\t\t*                               *");
    printf("\n\t\t*********************************");
    input:{
        fflush(stdin);
        printf("\n\n\t\t  Enter your choice (1/2/3/4): ");
        scanf("%d",&choice);
        if(choice==1){
            add_product();
        }
        else if(choice==2){
            calculate_total();
        }
        else if(choice==3){
            display_bill();
        }
        else if(choice==4){
            exit(0);
        }
        else{
            printf("\n\n\t\t  Invalid choice");
            goto input;
        }
    }
}

void main(){
    printf("\t\t*****************************");
    printf("\n\t\t*         Login Page        *");
    printf("\n\t\t*                           *");
    printf("\n\t\t*****************************");
    printf("\n\n\t\tUsername: admin");
    printf("\n\t\tPassword: ");
    char password[20];
    scanf("%[^\n]%*c",password);
    if(strcmp(password,"admin")==0){
        get_user_choice();
    }
    else{
        printf("\n\n\t\tIncorrect password\n\n");
        exit(0);
    }
    printf("\n\n");
    getch();
}
