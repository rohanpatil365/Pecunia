import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
class Demo
{
    public static void main(String arg[]) throws Exception
    {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do 
        {
            showMenu();
            choice = scanner.nextInt();
            switch(choice)
            {
                
                case 1:
                    //Create
                    saveCustomer();
                    break;
                case 2:
                    //Update
                    System.out.print("Enter Account No : ");
                    int accNo = scanner.nextInt();
                    updateCustomer(accNo);
                    break;
                case 3:
                    //Credit
                    System.out.print("Enter Account No : ");
                    int accountNo1 = scanner.nextInt();
                    if(isvalidAccount(accountNo1))
                    {
                        createTransaction(accountNo1,1);
                    }
                    else
                    {
                        System.out.println("Invalid Account Number !!!");
                    }
                    break;
                case 4:
                    //Debit
                    System.out.print("Enter Account No : ");
                    int accountNo2 = scanner.nextInt();
                    if(isvalidAccount(accountNo2))
                    {
                        createTransaction(accountNo2,2);
                    }
                    else
                    {
                        System.out.println("Invalid Account Number !!!");
                    }
                    break;
                case 5:
                    //Transaction
                    System.out.print("Enter Account No : ");
                    int accountNo3 = scanner.nextInt();
                    if(isvalidAccount(accountNo3))
                    {
                        List<String> entries = getPassbookEntry(accountNo3);
                        if(entries.size() > 0)
                        {
                            String balance = entries.get(entries.size()-1).split(",")[5];
                            String last_updated = entries.get(entries.size()-1).split(",")[2];
                            System.out.println("--------------------------Passbook--------------------------");
                            System.out.println("\n Your account balance : "+balance);
                            System.out.println(" Last Updated : "+last_updated);
                            System.out.println("\n Your Transactions :-");
                            System.out.println("\n |   Date   | Type | Transaction Amount |\tBalance\t|");
                            System.out.println(" --------------------------------------------------------");
                            for (String var : entries) {
                                String arr[] = var.split(",");
                                System.out.println(" | "+arr[2]+" |  "+arr[3]+"  |\t"+arr[4]+"\t\t|\t"+arr[5]+"\t|");
                            }
                            System.out.println("");
                        }
                        else
                        {
                            System.out.println("You do not have any transactions !!!");
                        }
                        
                    }
                    else
                    {
                        System.out.println("Invalid Account Number !!!");
                    }
                    break;
                case 6:
                    //Exit
                    System.out.println("Thank you !!!");
                    break;
                default:
                    System.out.println("Invalid Choice !!!");
                    break;
            }
        }while (choice != 6);
    }
    
    public static void showMenu()
    {
        System.out.println("\n-------------------- Welcome To Pecunia --------------------\n");
        System.out.println("\t1 : Create New Account");
        System.out.println("\t2 : Update Account");
        System.out.println("\t3 : Credit Into Account");
        System.out.println("\t4 : Debit From Account");
        System.out.println("\t5 : View Passbook");
        System.out.println("\t6 : Exit");
        System.out.println("\n------------------------------------------------------------");
    }

    public static int getLatestAccountNo() throws Exception
    {
        File file = new File("LatestAccountNumber.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int latest = -1;
        while((line = br.readLine())!= null)
        {
            latest = Integer.parseInt(line);
        }
        br.close();
        PrintWriter writer = new PrintWriter("LatestAccountNumber.txt", "UTF-8");
        writer.println(Integer.toString(latest+1));
        writer.close();
        return latest;
    }

    public static int getLatestTransactionNo() throws Exception
    {
        File file = new File("LatestTransactionNumber.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int latest = -1;
        while((line = br.readLine())!= null)
        {
            latest = Integer.parseInt(line);
        }
        br.close();
        PrintWriter writer = new PrintWriter("LatestTransactionNumber.txt", "UTF-8");
        writer.println(Integer.toString(latest+1));
        writer.close();
        return latest;
    }

    public static void saveCustomer() throws Exception
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nEnter Customer Name : \t");
        String cust_name = bufferedReader.readLine();
        System.out.print("Enter Account Type : \t");
        String cust_account_type = bufferedReader.readLine();
        System.out.print("Enter Contact Number : \t");
        String cust_contact_no = bufferedReader.readLine();
        int cust_account_no = getLatestAccountNo();
        System.out.println("Your Account No: \t"+cust_account_no);
        double balance = 0.0;
        System.out.println("Your Account Balance : \t"+balance);
        Customer customer = new Customer(cust_name,cust_account_type,cust_contact_no,cust_account_no,balance);
        String customerData = customer.getCustomerData();
        File customerFile = new File("CustomerDetails.csv");
        FileWriter fr = new FileWriter(customerFile,true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(customerData);
        br.newLine();
        br.close();
        fr.close();
        System.out.println("\n--------------- Account Created Successfully ---------------\n");
    }

    public static void updateCustomer(int accountNo)
    {
        
        try 
        {
            if(!isvalidAccount(accountNo))
            {
                System.out.println("invalid Account Number !!!");
            }
            else
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("CustomerDetails.csv"));
                String input;
                int count = 1;
                String customerRow = null;
                while((input = bufferedReader.readLine()) != null)
                {
                    if(count == accountNo)
                    {
                        customerRow = input;
                        break;
                    }
                    count++;
                }

                if(customerRow != null)
                {
                    BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Customer Details Found : "+customerRow);
                    System.out.print("Update Customer Name : \t");
                    String cust_name = inputBufferedReader.readLine();
                    System.out.print("Update Customer Contact : \t");
                    String cust_contact_no = inputBufferedReader.readLine();
                    Path FILE_PATH = Paths.get("CustomerDetails.csv");
                    List<String> fileContent = new ArrayList<>(Files.readAllLines(FILE_PATH, StandardCharsets.UTF_8));
                    String custArray[] = customerRow.split(",");
                    custArray[1] = cust_name;
                    custArray[3] = cust_contact_no;
                    String newData = String.join(",",custArray);
                    fileContent.set(accountNo - 1,newData);
                    Files.write(FILE_PATH, fileContent, StandardCharsets.UTF_8);
                }
                else
                    System.out.println("No customer found !!!");
                
            }    
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
            System.out.println("Error occured");
        }
        
    }

    public static boolean isvalidAccount(int accountNo)
    {
        try
        {
            File file = new File("LatestAccountNumber.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int latest = -1;
            while((line = br.readLine())!= null)
            {
                latest = Integer.parseInt(line);
            }
            br.close();
            if(accountNo < 1 || accountNo > latest)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch(Exception e)
        {
            System.out.println("Error occured while validating");
            return false;
        }
        
    }

    public static void createTransaction(int accountNo,int transType)
    {
        String transactionType;
        if(transType == 1)
        {
            transactionType = "CR";
        }
        else
        {
            transactionType = "DB";
        }
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("CustomerDetails.csv"));
            String input;
            int count = 1;
            String customerRow = getCustomerRow(accountNo);
            if(customerRow != null)
            {
                String custArray[] = customerRow.split(",");
                Scanner scanner = new Scanner(System.in);
                
                double oldBalance,newbalance,amount;

                //

                if(transType == 1)
                {
                    System.out.print("Enter Amount to be credited : ");
                    amount = scanner.nextDouble();
                    oldBalance = Double.parseDouble(custArray[4]);
                    newbalance = oldBalance + amount;
                    custArray[4] = Double.toString(newbalance);
                }
                else
                {
                    System.out.print("Enter Amount to be debited : ");
                    amount = scanner.nextDouble();
                    oldBalance = Double.parseDouble(custArray[4]);
                    if(amount > oldBalance)
                    {
                        System.out.println("Debit amount should be less than account balance !");
                        return;
                    }
                    else
                    {
                        newbalance = oldBalance - amount;
                        custArray[4] = Double.toString(newbalance);
                    }
                    
                }

                System.out.print("Enter Today's Date : ");
                String date = scanner.next();

                Path FILE_PATH = Paths.get("CustomerDetails.csv");
                List<String> fileContent = new ArrayList<>(Files.readAllLines(FILE_PATH, StandardCharsets.UTF_8));
                
                String newData = String.join(",",custArray);
                fileContent.set(accountNo - 1,newData);
                Files.write(FILE_PATH, fileContent, StandardCharsets.UTF_8);

                int latestId = getLatestTransactionNo();

                Transaction transaction = new Transaction(latestId,Integer.parseInt(custArray[0]),date,transactionType,amount,newbalance);
                String newTranscation = transaction.getTransaction();

                logTransaction(newTranscation);
            }
            else
            {
                System.out.println("No account found !!!");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error occured while credit :"+e);
        }
    }
    
    public static void logTransaction(String detail)
    {
        // Customer customer = new Customer(cust_name,cust_account_type,cust_contact_no,cust_account_no,balance);
        // String customerData = customer.getCustomerData();
        try
        {
            File transactionFile = new File("TransactionDetails.csv");
            FileWriter fr = new FileWriter(transactionFile,true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(detail);
            br.newLine();
            br.close();
            fr.close();
            System.out.println("\n--------------- Transaction Successful ---------------\n");
        }
        catch(Exception e)
        {
            System.out.println("Error OCcured");
        }
    }

    public static String getCustomerRow(int accountNo)
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("CustomerDetails.csv"));
            String input;
            int count = 1;
            String customerRow = null;
            while((input = bufferedReader.readLine()) != null)
            {
                if(count == accountNo)
                {
                    customerRow = input;
                    break;
                }
                count++;
            }
            return customerRow;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }

    public static List<String> getPassbookEntry(int accountNo)
    {
        List<String> entryList = new ArrayList<String>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("TransactionDetails.csv"));
            String input;
            int count = 1;
            while((input = bufferedReader.readLine()) != null)
            {
                String inputArray[] = input.split(",");
                if(Integer.parseInt(inputArray[1]) == accountNo)
                {
                    entryList.add(input);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error");
            return entryList;
        }
        
        return entryList;
    }
}