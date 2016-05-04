/**
 ******************************************************************************
 *                    HOMEWORK, 15-211
 ******************************************************************************
 *                    Amortized Dictionary
 ******************************************************************************
 *
 * A diagnostic class for your Dictionary implementation.
 *
 * Make sure you perform much more rigorous testing!!!
 * This class alone is *NOT* sufficient.
 *
 * For the Java literate, this class uses Java Exceptions and Reflections to
 * emulate the functionality of JUnit testing.
 *
 *****************************************************************************/


public class TestingDriver
{

	public static void simpleAddTest()
	{
		try
		{
			Dictionary<Integer> d = new Dictionary<Integer>();
			d.add(1);
			System.out.println(d.toString());
//			if(!d.toString().equals("0: [1]\n"))
//			{
//				throw new Exception();
//			}
			d.add(2);
			System.out.println(d.toString());
//			if(!d.toString().equals("1: [1, 2]\n"))
//			{
//				throw new Exception();
//			}
			d.add(3);
			System.out.println(d.toString());
//			if(!d.toString().equals("0: [3]\n1: [1, 2]\n"))
//			{
//				throw new Exception();
//			}
			d = new Dictionary<Integer>();
			for(int n = 1; n <= 16; n++)
			{
				d.add(n);
			}
			System.out.println(d.toString());
//			if(!d.toString().equals("4: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]\n"))
//			{
//				throw new Exception();
//			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Your add(e) definitely isn't working.");
		}
	}

	public static void simpleRemoveTest()
	{
		try
		{
			Dictionary<Integer> d = new Dictionary<Integer>();
			d.add(1);
			d.remove(1);
			if(!d.toString().equals(""))
			{
				throw new Exception();
			}
			d.add(1);
			d.add(2);
			d.add(3);
			d.remove(1);
			if(!d.toString().equals("1: [2, 3]\n"))
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Your remove(e) definitely isn't working.");
		}
	}

	public static void simpleContainsTest()
	{
		try
		{
			Dictionary<Integer> d = new Dictionary<Integer>();
			if(d.contains(1) != false)
			{
				throw new Exception();
			}
			d.add(1);
			if(d.contains(1) != true || d.contains(2) != false)
			{
				throw new Exception();
			}
			d.add(2);
			if(d.contains(1) != true || d.contains(2) != true || d.contains(3) != false)
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Your contains(e) definitely isn't working.");
		}
	}

	public static void simpleFrequencyTest()
	{
		try
		{
			Dictionary<Integer> d = new Dictionary<Integer>();
			for(int n = 1; n <= 100; n++)
			{
				int x = d.frequency(1);
				int y = d.frequency(2);
				if(x != n-1 || y != n-1)
				{
					throw new Exception();
				}
				d.add(1);
				d.add(2);
			}

		}
		catch(Exception e)
		{
			throw new RuntimeException("Your frequency(e) definitely isn't working.");
		}
	}

	public static void simpleSizeTest()
	{
		try
		{
			Dictionary<Integer> d = new Dictionary<Integer>();
			for(int n = 1; n <= 100; n++)
			{
				if(d.size() != n-1)
				{
					throw new Exception();
				}
				d.add(n);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Your size() definitely isn't working.");
		}
	}

	public static void main(String[] args)
	{
		boolean passedAllTests = true;

		Class<?> c = null;
		try
		{
			c = Class.forName("TestingDriver");
		}
		catch (ClassNotFoundException e1)
		{
			System.out.println("Something is wrong with your computer ... TestingDriver.java cannot be accessed.");
		}

		for(java.lang.reflect.Method m : c.getDeclaredMethods())
		{
			if(m.getName().indexOf("Test") == -1)
			{
				continue;
			}

			try
			{
				m.invoke(null);
			}
			catch(Exception e)
			{
				System.out.println(e.getCause().getMessage());
				passedAllTests = false;
			}
		}

		System.out.println();
		if(passedAllTests)
		{
			System.out.println("Looks good!  Make sure you do more rigorous testing.");
		}
		else
		{
			System.out.println("Try to fix the above problems.");
		}

	}
}
