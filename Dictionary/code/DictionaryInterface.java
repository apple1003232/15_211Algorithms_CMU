/**
 ******************************************************************************
 *                    HOMEWORK  15-211
 ******************************************************************************
 *                     Dictionary interface
 ******************************************************************************
 *****************************************************************************

               Do not modify this file.

 *****************************************************************************/

public interface DictionaryInterface<AnyType extends Comparable<AnyType>>
{
	/**
	 * Adds e to the dictionary.
	 */
	public void add(AnyType e);

	/**
	 * Removes one instance of e from the dictionary, if the item is in the dictionary.
	 * Otherwise, it does nothing.
	 */
	public void remove(AnyType e);

	/**
	 * Returns true if the dictionary contains an element equal to e,
	 * otherwise - false;
	 */
	public boolean contains(AnyType e);

	/**
	 * Returns the number of elements in the dictionary equal to e.
	 */
	public int frequency(AnyType e);
}
