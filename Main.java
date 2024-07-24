import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String[][] data = {
                {"1000", "2000", "12", "100.51"},
                {"1000", "2001", "31", "200"},
                {"1000", "2002", "22", "150.86"},
                {"1000", "2003", "41", "250"},
                {"1000", "2004", "55", "244"},
                {"1001", "2001", "88", "44.531"},
                {"1001", "2002", "121", "88.11"},
                {"1001", "2004", "74", "211"},
                {"1001", "2002", "14", "88.11"},
                {"1002", "2003", "2", "12.1"},
                {"1002", "2004", "3", "22.3"},
                {"1002", "2003", "8", "12.1"},
                {"1002", "2002", "16", "94"},
                {"1002", "2005", "9", "44.1"},
                {"1002", "2006", "19", "90"}
        };

        System.out.printf("Üç siparişteki urunlerin toplam tutarı: %.3f TL%n", calculateTotalAmount(data));
        System.out.printf("Üç siparişteki bütün urunlerin ortalama fiyatı: %.3f TL%n", calculateAveragePrice(data));
        printAveragePricePerItem(data);
        printItemOrderCounts(data);
    }

    // 1a. Üç siparişteki ürünlerin toplam tutarının çıktısını veren fonksiyon
    public static double calculateTotalAmount(String[][] data) {
        double totalAmount = 0;
        for (String[] row : data) {
            int quantity = Integer.parseInt(row[2]);
            double unitPrice = Double.parseDouble(row[3].replace(",", "."));
            totalAmount += quantity * unitPrice;
        }
        return totalAmount;
    }

    // 1b. Üç siparişteki bütün ürünlerin ortalama fiyatını bulan fonksiyon
    public static double calculateAveragePrice(String[][] data) {
        double totalUnitPrice = 0;
        int totalItems = 0;
        for (String[] row : data) {
            double unitPrice = Double.parseDouble(row[3].replace(",", "."));
            totalUnitPrice += unitPrice;
            totalItems++;
        }
        return totalUnitPrice / totalItems;
    }

    // 1c. Üç siparişteki bütün ürünlerin tek tek ürün bazlı ortalama fiyatını bulan fonksiyon
    public static void printAveragePricePerItem(String[][] data) {
        Map<String, Double> totalPrices = new HashMap<>();
        Map<String, Integer> itemCounts = new HashMap<>();

        for (String[] row : data) {
            String item = row[1];
            double unitPrice = Double.parseDouble(row[3].replace(",", "."));

            totalPrices.put(item, totalPrices.getOrDefault(item, 0.0) + unitPrice);
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }

        for (Map.Entry<String, Double> entry : totalPrices.entrySet()) {
            String item = entry.getKey();
            double totalPrice = entry.getValue();
            int count = itemCounts.get(item);
            double averagePrice = totalPrice / count;
            System.out.printf("Urun numarası %s için ortalama fiyat: %.3f TL%n", item, averagePrice);
        }
    }

    // 1d. Tek tek ürün bazlı, ürünlerin hangi siparişlerde kaç adet olduğunun çıktısını veren fonksiyon
    public static void printItemOrderCounts(String[][] data) {
        Map<String, Map<String, Integer>> itemOrderCounts = new HashMap<>();

        for (String[] row : data) {
            String order = row[0];
            String item = row[1];
            int quantity = Integer.parseInt(row[2]);

            itemOrderCounts.putIfAbsent(item, new HashMap<>());
            Map<String, Integer> orderCounts = itemOrderCounts.get(item);
            orderCounts.put(order, orderCounts.getOrDefault(order, 0) + quantity);
        }

        for (Map.Entry<String, Map<String, Integer>> entry : itemOrderCounts.entrySet()) {
            String item = entry.getKey();
            Map<String, Integer> orderCounts = entry.getValue();
            System.out.printf("Urun numarası %s için siparişler:%n", item);
            for (Map.Entry<String, Integer> orderEntry : orderCounts.entrySet()) {
                System.out.printf("  Sipariş %s: %d adet%n", orderEntry.getKey(), orderEntry.getValue());
            }
        }
    }
}
