package cmd;
//fkotoni21
import java.util.Scanner;

public class MainApp
{
	public static void main(String[] args)
	{
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size: ");
        
        MyHashMap<String, String> map = new MyHashMap<String, String>(sc.nextInt());
        char ans;

        do   
        {
            System.out.print("Press 1 to insert, 2 to remove, and 3 to access an element: ");
            int op = sc.nextInt();            
            switch (op)
            {
            case 1:
                System.out.println("Enter the key and value of the element to be inserted:");
                map.put(sc.next(), sc.next()); 
                break;                          
            case 2:                 
                System.out.println("Enter the key of the element to be deleted:");
                map.remove(sc.next()); 
                break;
            case 3:
            	System.out.print("Enter a key in order to get its value: ");
                System.out.println(map.get(sc.next()));
                break;
            default: 
                System.out.println("Invalid operation ID.");
                break;   
            }

            map.printList(); System.out.println();
            System.out.print("Continue? (Press Y for Yes, and N for No) ");
            ans = sc.next().charAt(0);                        

        } while (ans == 'Y'|| ans == 'y');
    }
}
