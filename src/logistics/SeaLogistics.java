package logistics;

/**
 * [Concrete Creator 2] theo Refactoring Guru.
 * Quản lý vận tải đường biển -> Khởi tạo Ship.
 */
public class SeaLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}

