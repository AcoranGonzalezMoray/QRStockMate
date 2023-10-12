namespace QRStockMate.Model
{
    public class ItemModel
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public int warehouseId { get; set; }

        public string location { get; set; }

        public int stock { get; set; }

        public string url { get; set; }
    }
}
