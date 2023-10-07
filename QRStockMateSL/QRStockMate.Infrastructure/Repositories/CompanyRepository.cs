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
    internal class CompanyRepository : BaseRepository<Company>, ICompanyRepository
    {

        private readonly ApplicationDbContext _context;
        private readonly DbSet<Company> _entities;
        public CompanyRepository(ApplicationDbContext context) : base(context)
        {
            _context = context;
            _entities = _context.Set<Company>();
        }

        public Task<IEnumerable<Company>> getEmployees(string code)
        {
            throw new NotImplementedException();//Debo Implementar
        }

        public Task<IEnumerable<Company>> getWarehouses()
        {
            throw new NotImplementedException();//Santiago Debe Implementar
        }
    }
}
