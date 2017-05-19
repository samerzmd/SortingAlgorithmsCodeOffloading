package apps.buildable.sortingalgorithms.sort.enums;

/**
 * Created by SamerGigaByte on 5/19/2017.
 */

public enum SortingAlgorithmType {
    QuickSort("QuickSort"),InsertionSort("InsertionSort");
    private final String value;

    SortingAlgorithmType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortingAlgorithmType getAlgorithm(String value) {
        for (SortingAlgorithmType type :
                SortingAlgorithmType.values()) {
            if (type.getValue().equals(value))
                return type;
        }
        return null;
    }
}
