using Microsoft.EntityFrameworkCore;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.Infrastructure.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QRStockMate.Infrastructure.Repositories
{
    public class WarehouseRepository : BaseRepository<Warehouse>, IWarehouseRepository
    {
        private readonly ApplicationDbContext _context;

        public WarehouseRepository(ApplicationDbContext context) : base(context)
        {
            _context = context;
        }

        

        public async Task<User> GetAdministrator(int WarehouseId)
        {
            var Warehouse = await _context.Warehouses.Where(w => w.Id == WarehouseId).FirstOrDefaultAsync();
            return await _context.Users.Where(u => u.Id == Warehouse.IdAdministrator).FirstOrDefaultAsync();
            
        }

        public async Task<string> GetLocation(int WarehouseId)
        {
            var Warehouse = await _context.Warehouses.Where(w => w.Id == WarehouseId).FirstOrDefaultAsync();
            return Warehouse.Location;
        }

        public async Task<string> GetName(int WarehouseId)
        {
            var Warehouse = await _context.Warehouses.Where(w => w.Id == WarehouseId).FirstOrDefaultAsync();
            return Warehouse.Name;
        }

        public async Task<string> GetOrganization(int WarehouseId)
        {
            var Warehouse = await _context.Warehouses.Where(w => w.Id == WarehouseId).FirstOrDefaultAsync();
            return Warehouse.Organization;
        }
    }
}
