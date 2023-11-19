import java.util.*;

class patron{
    //a structural library patron with these attributes
    private String name;
    private String ID;
    private ArrayList<String> barrowedBooks;
    patron(String name,String ID,ArrayList<String> barrowedBooks)
    {
        this.name=name;
        this.ID=ID;
        this.barrowedBooks=barrowedBooks;
    }
    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public ArrayList<String> getBarrowedBooks() {
        return barrowedBooks;
    }
}

interface Borrowable{
    //creating interface barrowable with the methods barrow() and returnBook()
    void barrow(String ID);
    void returnBook(String ISBN);
}

//creating class patronmanagement to implement interface Barrowable
class patronManagement implements Borrowable{
    Scanner sc=new Scanner(System.in);
    HashMap<String,patron> PatronList=new HashMap<>();
    void addPatron(patron Patron){
        //add a new patron to library
        if(!PatronList.containsKey(Patron.getID()))
        {

            //add patron to the list if not exist
           PatronList.put(Patron.getID(), Patron);
        }
        else{
            System.out.println("User with Specified ID already exixt\n");
        }

    }
    void removePatron(String ID){
        //removing a patron from library
        if(PatronList.containsKey(ID))
        {
            //remove patron if exist with given ID
            PatronList.remove(ID);
        }
        else{
            System.out.println("User with specified ID not exit in the List\n\n");
        }

    }
    void displayPatron(){
        //display the all the patrons there in library register
        System.out.println("---------------------------Patrons------------------------------------");
        int count=1;
        for(Map.Entry<String,patron> entry: PatronList.entrySet())
        {
            patron Patron=entry.getValue();
            System.out.println("user "+count);
            System.out.println("ID: "+Patron.getID());
            System.out.println("Name: "+Patron.getName());
            System.out.println("Barrowed Books: ");
            System.out.println(Patron.getBarrowedBooks());
            count++;
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------------\n");

    }
    public void barrow(String ID){
        //barrowing the books form Library with specified ISBN
        patron Patron=PatronList.get(ID);
        System.out.println("Enter the Book ISBN number : ");
        String ISBN=sc.next();
        //selecting genre of Book
        System.out.println("Select genre type: \n1.Fiction\n2.NonFiction\n");
        int op=sc.nextInt();
        switch (op) {
            case 1:
                //if genre id Fictional

                if(!FictionBook.FBook.containsKey(ISBN) || FictionBook.FBook.get(ISBN).getQuantity()==0)
                {
                    //Threre is no such book with ISBN or No stock
                    System.out.println("The book with This ISBN Doesn't exist or book is out of stock\n");
                }
                else{
                    //adding book to the list of patron collection and removing count from book collection
                    Patron.getBarrowedBooks().add(FictionBook.FBook.get(ISBN).getTitle());
                    FictionBook.FBook.get(ISBN).setQuantity(FictionBook.FBook.get(ISBN).getQuantity()-1);
                }
                break;
            case 2:
                //if Genre is NonFiction
                if(!NonFictionBook.NfBook.containsKey(ISBN) || NonFictionBook.NfBook.get(ISBN).getQuantity()==0)
                {
                    //Threre is no such book with ISBN or No stock
                    System.out.println("The book with This ISBN Doesn't exist or book is out of stock\n");
                }
                else{
                    //adding book to the list of patron collection and removing count from book collection
                    Patron.getBarrowedBooks().add(NonFictionBook.NfBook.get(ISBN).getTitle());
                    NonFictionBook.NfBook.get(ISBN).setQuantity(NonFictionBook.NfBook.get(ISBN).getQuantity()-1);
                }
                break; 
            default:
                System.out.println("Invalid option\n\n");
                break;
        }
    }
    public void returnBook(String ISBN){
        //submitting the barrowed books to library
        if(FictionBook.FBook.containsKey(ISBN) || NonFictionBook.NfBook.containsKey(ISBN))
        {
            //if Book with specified ISBN exist then only return book 
            System.out.println("Enter Your ID Number: ");
            String ID=sc.next();
            System.out.println("---------------------------------------------------------------");
            System.out.println("Select genre type: \n1.Fiction\n2.NonFiction\n");
            int op=sc.nextInt();
            String name;
            switch (op) {
                case 1:
                    //if return book is Fiction Book 
                    int qunatity1=FictionBook.FBook.get(ISBN).getQuantity();
                    //adding book to books collection
                    FictionBook.FBook.get(ISBN).setQuantity(qunatity1+1);
                    name=FictionBook.FBook.get(ISBN).getTitle();
                    //removing the Book from patron collection
                    PatronList.get(ID).getBarrowedBooks().remove(name);
                    break;
                case 2:
                    //NonFictional Book
                    int qunatity2=NonFictionBook.NfBook.get(ISBN).getQuantity();
                    //adding book to books collection
                    NonFictionBook.NfBook.get(ISBN).setQuantity(qunatity2+1);
                    name=NonFictionBook.NfBook.get(ISBN).getTitle();
                    //removing the Book from patron collection
                    PatronList.get(ID).getBarrowedBooks().remove(name);
                    break; 
                default:
                    System.out.println("Invalid option\n\n");
                    break;
            }
        }
        else{
            System.out.println("There is no such book with specified ISBN number\n\n");
        }
    }
}
class Book{
    //structural books to manage with these attributes
    private String title;
    private String author;
    private String ISBN;
    private int quantity;
    Book( String title,String author,String ISBN,int quantity){
        this.title=title;
        this.author=author;
        this.ISBN=ISBN;
        this.quantity=quantity;
    }
    //setter and getters for private attributes
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getISBN() {
        return ISBN;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
class FictionBook{
    //managing the books of fictional
    public static HashMap<String,Book> FBook=new HashMap<>();

     //ADD A new book to the library
    void addBook(Book book){
       
        if(!FBook.containsKey(book.getISBN()))
        {
            //if no such book with ISBN exist
            FBook.put(book.getISBN(),book);
        }
        else{
            //if already ISBN number exist
            Book book1=FBook.get(book.getISBN());
            if(book1.getTitle()==book.getTitle() && book1.getAuthor()==book.getAuthor())
            {
                //updating the quantity of books
                book1.setQuantity(book.getQuantity()+book1.getQuantity());
            }
            else{
                //if both books are different with same ISBN
                System.out.println("The book with specified ISBN number already exist\n");;
            }
        }
    }
    void removeBook(String ISBN){
        //remove a book from library
        if(FBook.containsKey(ISBN))
        {
            //if Book exist with ISBN
            FBook.remove(ISBN);
        }
        else{
            //there is no book available with this ISBN number
            System.out.println("Threre is no Book available with specified ISBN number\n\n");
        }
    }
    void displayBooks(){
        //display the book to the users 
        System.out.println("-------------------------------------------Fiction Books---------------------------------------\n\n");
        for(Map.Entry<String,Book> entry: FBook.entrySet())
        {
            Book b=entry.getValue();
            System.out.println("Title: "+b.getTitle());
            System.out.println("Author: "+b.getAuthor());
            System.out.println("ISBN: "+b.getISBN());
            System.out.println("Quantity: "+b.getQuantity());
            System.out.println();  //new line
        }
        System.out.println("------------------------------------------------------------------------------------------------\n");
    }
}
class NonFictionBook{
    //managing thebooks of nonfictional 
    public static HashMap<String,Book> NfBook=new HashMap<>();
    void addBook(Book book){
        //ADD A new book to the library
        if(!NfBook.containsKey(book.getISBN()))
        {
            //if no such book with ISBN exist
            NfBook.put(book.getISBN(),book);
        }
        else{
            //if already ISBN number exist
            Book book2=NfBook.get(book.getISBN());
            if(book2.getTitle()==book.getTitle() && book.getAuthor()==book2.getAuthor())
            {
                //updating the quantity of books
                book2.setQuantity(book.getQuantity()+book2.getQuantity());
            }
            else{
                //if both books are different with same ISBN
                System.out.println("Threre is no Book available with specified ISBN number\n\n");
            }
        }
    }
    void removeBook(String ISBN){
        //remove a book from library
        if(NfBook.containsKey(ISBN))
        {
             //if Book exist with ISBN
            NfBook.remove(ISBN);
        }
        else{
            //there is no book available with this ISBN number
            System.out.println("Threre is no Book available with specified ISBN number\n\n");
        }
    }
    void displayBooks(){
        //display the book to the users 
        System.out.println("-------------------------------------NonFiction Books------------------------------------------\n\n");
        for(Map.Entry<String,Book> entry: NfBook.entrySet())
        {
            Book b=entry.getValue();
            System.out.println("Title: "+b.getTitle());
            System.out.println("Author: "+b.getAuthor());
            System.out.println("ISBN: "+b.getISBN());
            System.out.println("Quantity: "+b.getQuantity());
            System.out.println();  //new line
        }
        System.out.println("----------------------------------------------------------------------------------------------\n");
    }
}

class Library{
    
    public static void main(String args[])
    {
        //here starts the program execution
        patronManagement Pmanagement=new patronManagement();
        NonFictionBook NonFiction=new NonFictionBook();
        FictionBook Fiction=new FictionBook();
        Scanner sc=new Scanner(System.in);
        int option;
        String title,author,ISBN;
        int Quantity;
        Book book;
        while(true)
        {
            System.out.println("Select the option that are mentioned below: ");
            System.out.println(
                            "1. Add Book\n"+
                           "2. Add a Patron\n"+
                            "3. Remove a Book\n"+
                            "4. Remove a Patron\n"+
                            "5. Display Available Books\n"+
                           "6. Display Patrons\n"+
                            "7. Barrow a Book\n"+
                            "8. Return a Book\n"+
                            "9. Exit the System\n");
            System.out.print("\noption: ");
            option=sc.nextInt();
            switch(option)
            {
                case 1:
                    //add book
                    System.out.println("Enter the book details: \n");
                    System.out.println("-----------------------------------Book------------------------------------------");
                    System.out.print("Title: ");
                    title=sc.next();
                    System.out.print("Author: ");
                    author=sc.next();
                    System.out.print("ISBN: ");
                    ISBN=sc.next();
                    System.out.print("Quantity: ");
                    Quantity=sc.nextInt();
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("Select genre type: \n1.Fiction\n2.NonFiction\n");
                    int op=sc.nextInt();
                    //based on genre adding Books to Library
                    switch (op) {
                        case 1:
                            book=new Book(title, author, ISBN, Quantity);
                            Fiction.addBook(book);
                            break;
                        case 2:
                            book=new Book(title, author, ISBN, Quantity);
                            NonFiction.addBook(book);
                            break; 
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                    System.out.println();
                    break;
                case 2:
                    //adding a patron to List
                    String name,ID;
                    System.out.println("Enter the Patron details properly: \n");
                    System.out.print("Name: ");
                    name=sc.next();
                    System.out.print("ID: ");
                    ID=sc.next();
                    patron patron1=new patron(name, ID,new ArrayList<>());
                    Pmanagement.addPatron(patron1);
                    break;
                case 3:
                    //removing a book from Collection with ISBN
                    System.out.println("Enter the book ISBN number: ");
                    ISBN=sc.next();
                    System.out.println("Select genre type: \n1.Fiction\n2.NonFiction\n");
                    op=sc.nextInt();
                    switch (op) {
                        case 1:
                            Fiction.removeBook(ISBN);
                            break;
                        case 2:
                            NonFiction.removeBook(ISBN);
                            break; 
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case 4:
                    //removing  patron based on ID number
                    System.out.print("Enter the book ISBN number: ");
                    ID=sc.next();
                    Pmanagement.removePatron(ID);
                    break;
                case 5:
                    //displaying  books Fictional and NonFiction
                    Fiction.displayBooks();
                    NonFiction.displayBooks();
                    break;
                case 6:
                    //display patrons
                    Pmanagement.displayPatron();
                    break;
                case 7:
                    //barrow a book from Library
                    System.out.print("Enter your ID number: ");
                    ID=sc.next();
                    Pmanagement.barrow(ID);
                    break;
                case 8:
                    //return a book to Library Collection
                    System.out.print("Enter Book ISBN number ");
                    ISBN=sc.next();
                    Pmanagement.returnBook(ISBN);
                    break;
                case 9:
                    //End Application
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input\n");
            }
            System.out.println("---------------------------------------------------------------------------------------------");
        }

    }
}