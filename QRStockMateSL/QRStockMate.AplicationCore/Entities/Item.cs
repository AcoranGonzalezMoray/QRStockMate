using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QRStockMate.AplicationCore.Entities
{
    public class Item
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public int warehouseId { get; set; }

        public string location { get; set; }

        public int stock { get; set; }

        public string url { get; set; }
    }
}
