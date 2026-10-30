package logistics;


public abstract class Logistics {


    public abstract Transport createTransport();

    public String planDelivery(String shipmentId) {
        System.out.println("------------------------------------------------------------------");
        System.out.printf("[LOGISTICS HUB] Bắt đầu lên kế hoạch vận chuyển kiện hàng: #%s\n", shipmentId);

        // Gọi Factory Method để tạo đối tượng Transport phù hợp
        Transport transport = createTransport();

        // Thực hiện giao hàng thông qua Interface chung
        String deliveryMessage = transport.deliver();

        System.out.printf("[LOGISTICS HUB] Kiện hàng #%s đã được xử lý và xuất kho thành công!\n", shipmentId);
        System.out.println("------------------------------------------------------------------");
        return deliveryMessage;
    }
}

