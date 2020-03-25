import java.util.Hashtable;
import java.util.LinkedList;

import static java.lang.Math.sqrt;

public class HashElement {

    Integer value;
    Integer occurancesNumber;
    boolean checked;

    public HashElement(Integer value) {
        this.value = value;
        this.occurancesNumber = 1;
        this.checked = false;

    }

    public HashElement(Integer value, Integer occurancesNumber) {
        this.value = value;
        this.occurancesNumber = occurancesNumber;
        this.checked = false;
    }

    private static Hashtable<Integer, HashElement> occurancesTable(Integer[] testTable) {
        Hashtable<Integer, HashElement> hashTable = new Hashtable<Integer, HashElement>();

        for (Integer element : testTable)
            if (!hashTable.containsKey(element)) {
                HashElement newElement = new HashElement(element);
                hashTable.put(element, newElement);
            } else {
                hashTable.compute(element, (key, val) -> val.updateOccurances());
            }

        return hashTable;
    }

    public static LinkedList<Integer> rewriteTable(Integer[] mainTable, Integer[] testTable) {

        Hashtable<Integer, HashElement> hashtable = new Hashtable<Integer, HashElement>();
        LinkedList<Integer> resultElements = new LinkedList<Integer>();

        hashtable = occurancesTable(testTable);
        HashElement currentElement;

        for (Integer element : mainTable) {
            if(!hashtable.containsKey(element)) {
                resultElements.add(element);
                continue;
            }

            currentElement = hashtable.get(element);
            if (currentElement.checked && currentElement.value == 0){
                resultElements.add(element);
            }
            else if (!currentElement.checked) {
                currentElement.checked = true;
                if (currentElement.occuranceNumberIsPrime())
                    currentElement.value = 1;

                else {
                    currentElement.value = 0;
                    resultElements.add(element);
                }

            }

        }

        return resultElements;
    }

    public static void main(String[] args) {
        Integer[] A = {2, 3, 9, 2, 5, 1, 3, 7, 10};
        Integer[] B = {2, 1, 3, 4, 3, 10, 6, 6, 1, 7, 10, 10, 10};
        LinkedList<Integer> C;

        C = rewriteTable(A, B);
        System.out.println(C.toString());
    }

    private HashElement updateOccurances() {
        this.occurancesNumber++;
        return this;
    }

    private boolean occuranceNumberIsPrime() {
        if (this.occurancesNumber == 0 || this.occurancesNumber == 1) return false;
        if (this.occurancesNumber == 2 || this.occurancesNumber == 3) return true;
        else if (this.occurancesNumber % 2 == 0 || this.occurancesNumber % 3 == 0) return false;

        for (Integer i = 5; i <= sqrt(this.occurancesNumber); i += 6) {
            if (this.occurancesNumber % i == 0 || this.occurancesNumber % (i + 2) == 0) return false;
        }
        return true;
    }

}
