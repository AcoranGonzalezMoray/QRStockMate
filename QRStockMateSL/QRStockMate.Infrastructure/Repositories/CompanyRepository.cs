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
    public class CompanyRepository : BaseRepository<Company>, ICompanyRepository
    {

        private readonly ApplicationDbContext _context;
        public CompanyRepository(ApplicationDbContext context) : base(context)
        {
            _context = context;
        }

        public async Task<Company> getCompanyByCode(string code)
        {
            return await _context.Companies.Where(a => a.Code == code).FirstOrDefaultAsync();
        }

        public async Task<IEnumerable<User>> getEmployees(string code)
        {
            
            return await _context.Users.Where(a => a.Code == code).ToListAsync();
        }

        public Task<IEnumerable<Company>> getWarehouses(string code)
        {
            throw new NotImplementedException();//Santiago Debe Implementar
        }
    }
}
