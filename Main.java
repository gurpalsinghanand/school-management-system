
package Innovative_Assignment;

import java.util.Scanner;
import java.io.*;

public class Main
{
  public static void main (String[]args)
  {
    int state = 0, n;
    do{
      System.out.println ("1. for Student 2. for faculty 3. for Shopkeeper");
    Scanner sc = new Scanner (System.in);
      n = sc.nextInt ();
    faculty_loc f = new faculty_loc ();
    shop s = new shop ();
    switch (n)
      {
      case 1:
	System.out.println ("Enter grade are you in? :");
	int std;
	  std = sc.nextInt ();
	  System.out.println ("Enter your roll number? :");
	int roll;
	  roll = sc.nextInt ();
	  System.out.println("Press 1 for information about faculty \n2 for information about shop");
	int check = sc.nextInt ();
	if (check == 1)
	  {
	    System.out.println("Enter the name of faculty you want to know location in campus");
	    String f_name = sc.nextLine ();//  \n gets in this scan
            f_name = sc.nextLine ();
	      f.get_loc (f_name);
	  }
	else if (check == 2)
	  {
            if(state == 1){
	    String item_name = sc.next (); 
	    s.availability (item_name);
            }
            else
            {
                System.out.println("Shop is closed");
            }
          }
	break;
      case 2:
	System.out.println ("Enter your name : ");
	String name = new String ();
	name = sc.nextLine (); //  \n gets in this scan
        name = sc.nextLine ();
	System.out.println("Press 1 to create entry \n2 to update location \n3 to delete entry for the day");
	int loc = sc.nextInt ();
	if (loc == 1)
	  {
	    System.out.println ("Enter your room number :");
	    int no = sc.nextInt ();
	    f.create (name, no);
	  }
	else if (loc == 2)
	  {
	    System.out.println ("Enter your room number :");
	    int no = sc.nextInt ();
	    f.update (name, no);
	  }
	else
	  {
	    f.delete_loc (name);
	  }
	break;
      case 3:
	System.out.println ("Press 1 to update stocks \n2 to update state of shop");
	int shop = sc.nextInt ();
	if (shop == 1)
	  {
	    System.out.println ("Enter name of item to change stocks of ");
	    String item = sc.next (); 
	    System.out.println ("Enter the stock details");
	    int item_no = sc.nextInt ();
	    s.shop_stocks (item, item_no);
	  }
	else if (shop == 2)
	  {
	    System.out.println ("Press 1 for Open \n2 for Close");
	    state = sc.nextInt ();
	  }
	break;
      default:
	break;
      }
    }while(n<=3&&n>=1);
  }
}

class faculty_loc
{
  File file;
    faculty_loc ()
  {
    try
    {
      file = new File ("Faculty_loc.txt");
      if (!file.exists ())
	{
	  file.createNewFile ();	// Creates a new file if it doesnot exists. 
	}
    }
    catch (IOException e)
    {
      System.out.println ("Exception Occurred");
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.println ("Error");
    }
  }
  void create (String name, int no)
  {
    String data = new String ();
    String rname = new String ();
    try
    {				// Opening file in reading and write mode.
      RandomAccessFile raf = new RandomAccessFile (file, "rw");
      boolean found = false;
      while (raf.getFilePointer () < raf.length ())
	{
	  data = raf.readLine ();
	  int index = data.indexOf ('|');
	  rname = data.substring (0, index);
	  if (rname.equals (name))
	    {
	      found = true;
	      break;
	    }
	}
      if (found == false)
	{
	  String d = name + "|" + no;
	  raf.writeBytes (d);	// To insert the next record in new line.
	  raf.writeBytes (System.lineSeparator ());
	  System.out.println (" Entry added. ");
	  raf.close ();
	}
      else
	{
	  raf.close ();
	  System.out.println ("Input name already exists");
	}
    }
    catch (IOException e)
    {
      System.out.println ("exception occured");
    }
  }
  void update (String name, int no)
  {
    String data = new String ();
    String rname = new String ();
    int index;
    try
    {				// Opening file in reading and write mode. 
      RandomAccessFile raf = new RandomAccessFile (file, "rw");
      boolean found = false;
      while (raf.getFilePointer () < raf.length ())
	{
	  data = raf.readLine ();
	  index = data.indexOf ('|');
	  rname = data.substring (0, index);
	  if (rname.equals (name))
	    {
	      found = true;
	      break;
	    }
	}
      if (found == true)
	{
	  String d = name + "|" + no;
	  File tmpFile = new File ("temp.txt");
	  RandomAccessFile tmpraf = new RandomAccessFile (tmpFile, "rw");
	  raf.seek (0);		// Set file pointer to start 
	  while (raf.getFilePointer () < raf.length ())
	    {
	      data = raf.readLine ();	// Reading the contact from the file 
	      index = data.indexOf ('|');
	      rname = data.substring (0, index);
	      if (rname.equals (name))
		{
		  data = name + "|" + no;
		}
	      tmpraf.writeBytes (data);	// Add this contact in the temporary file 
	      tmpraf.writeBytes (System.lineSeparator ());	// Add the line separator in the temporary file 
	    }
	  raf.seek (0);
	  tmpraf.seek (0);
	  while (tmpraf.getFilePointer () < tmpraf.length ())
	    {
	      raf.writeBytes (tmpraf.readLine ());
	      raf.writeBytes (System.lineSeparator ());
	    }
	  raf.setLength (tmpraf.length ());
	  tmpraf.close ();
	  raf.close ();
	  tmpFile.delete ();	// Deleting the temporary file
	  System.out.println (" Entry updated. ");
	}
      else
	{
	  raf.close ();
	  System.out.println (" Input name does not exist");
	}
    }
    catch (IOException e)
    {
      System.out.println ("exception occured");
    }
  }
  void delete_loc (String name)
  {
    String data = new String ();
    String rname = new String ();
    int index;
    try
    {				// Opening file in reading and write mode. 
      RandomAccessFile raf = new RandomAccessFile (file, "rw");
      boolean found = false;
      while (raf.getFilePointer () < raf.length ())
	{
	  data = raf.readLine ();
	  index = data.indexOf ('|');
	  rname = data.substring (0, index);
	  if (rname.equals (name))
	    {
	      found = true;
	      break;
	    }
	}
      if (found == true)
	{
	  File tmpFile = new File ("temp.txt");
	  RandomAccessFile tmpraf = new RandomAccessFile (tmpFile, "rw");
	  raf.seek (0);		// Set file pointer to start 
	  while (raf.getFilePointer () < raf.length ())
	    {
	      data = raf.readLine ();	// Reading the contact from the file 
	      index = data.indexOf ('|');
	      rname = data.substring (0, index);
	      if (rname.equals (name))
		{
		  continue;
		}
	      tmpraf.writeBytes (data);	// Add this contact in the temporary file 
	      tmpraf.writeBytes (System.lineSeparator ());	// Add the line separator in the temporary file 
	    }
	  raf.seek (0);
	  tmpraf.seek (0);
	  while (tmpraf.getFilePointer () < tmpraf.length ())
	    {
	      raf.writeBytes (tmpraf.readLine ());
	      raf.writeBytes (System.lineSeparator ());
	    }
	  raf.setLength (tmpraf.length ());
	  tmpraf.close ();
	  raf.close ();
	  // Deleting the temporary file 
	  tmpFile.delete ();
	  System.out.println (" Entry deleted for the day. ");
	}
      else
	{
	  raf.close ();
	  System.out.println (" Input name does not exist");
	}
    }
    catch (IOException e)
    {
      System.out.println ("exception occured");
    }
  }

  void get_loc (String name)
  {
    String data = new String ();
    String rname = new String ();
    int index = 0;
    try
    {
      // Opening file in reading and write mode. 
      RandomAccessFile raf = new RandomAccessFile (file, "rw");
      boolean found = false;
      while (raf.getFilePointer () < raf.length ())
	{
	  data = raf.readLine ();
	  index = data.indexOf ('|');
	  rname = data.substring (0, index);
	  if (rname.equals (name))
	    {
	      found = true;
	      break;
	    }
	}
      if (found == true)
	{
	  System.out.println ("Room No." + data.substring (index + 1));
	}
      if (found == false)
	System.out.println ("No data found");
    }
    catch (IOException e)
    {
      System.out.println ("exception occured");
    }
  }
}

class shop
{
  File file;
    shop ()
  {
    try
    {
      file = new File ("shop.txt");
      if (!file.exists ())
	{
	  // Create a new file if not exists. 
	  file.createNewFile ();
	}
    }
    catch (IOException e)
    {
      System.out.println ("Exception Occurred");
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.println ("Error");
    }
  }
  void shop_stocks (String item, int no)
  {
    String data = new String ();
    String rname = new String ();
    int index;
    try
    {
      // Opening file in reading and write mode. 
      RandomAccessFile raf = new RandomAccessFile (file, "rw");
      boolean found = false;
      while (raf.getFilePointer () < raf.length ())
	{
	  data = raf.readLine ();
	  index = data.indexOf ('|');
	  rname = data.substring (0, index);
	  if (rname.equals (item))
	    {
	      found = true;
	      break;
	    }
	}
      if (found == true)
	{
	  String d = item + "|" + no;
	  File tmpFile = new File ("temp.txt");
	  RandomAccessFile tmpraf = new RandomAccessFile (tmpFile, "rw");
	  // Set file pointer to start 
	  raf.seek (0);
	  while (raf.getFilePointer () < raf.length ())
	    {
	      // Reading the contact from the file 
	      data = raf.readLine ();
	      index = data.indexOf ('|');
	      rname = data.substring (0, index);
	      if (rname.equals (item))
		{
		  data = item + "|" + no;
		}		// Add this contact in the temporary file 
	      tmpraf.writeBytes (data);
	      // Add the line separator in the temporary file 
	      tmpraf.writeBytes (System.lineSeparator ());
	    }
	  raf.seek (0);
	  tmpraf.seek (0);
	  while (tmpraf.getFilePointer () < tmpraf.length ())
	    {
	      raf.writeBytes (tmpraf.readLine ());
	      raf.writeBytes (System.lineSeparator ());
	    }
	  raf.setLength (tmpraf.length ());
	  tmpraf.close ();
	  raf.close ();
	  // Deleting the temporary file 
	  tmpFile.delete ();
	  System.out.println (" Entry updated. ");
	}
      else
	{
	  raf.close ();
	  System.out.println (" Input name does not exits...");
	}
    }
    catch (IOException e)
    {
      System.out.println ("exception occured");
    }

  }
  void availability (String item_name)
  {
    String data = new String ();
    String rname = new String ();
    int index = 0;
    try
    {
      RandomAccessFile raf = new RandomAccessFile (file, "rw");
      boolean found = false;
      while (raf.getFilePointer () < raf.length ())
	{
	  data = raf.readLine ();
	  index = data.indexOf ('|');
	  rname = data.substring (0, index);
	  if (rname.equals (item_name))
	    {
	      found = true;
	      break;
	    }
	}
      if (found == true)
	{
	  System.out.println ("Item available");
	}
      if (found == false)
	System.out.println ("Item not available");
    }
    catch (IOException e)
    {
      System.out.println ("exception occured");
    }

  }
}