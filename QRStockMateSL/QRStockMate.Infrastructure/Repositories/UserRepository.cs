using Microsoft.EntityFrameworkCore;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.Infrastructure.Data;
using static System.Reflection.Metadata.BlobBuilder;

namespace QRStockMate.Infrastructure.Repositories
{
    public class UserRepository : BaseRepository<User>, IUserRepository
    {

        private readonly ApplicationDbContext _context;
        

        public UserRepository(ApplicationDbContext context) : base(context)
        {
            _context = context;
            
        }

        public async Task DeleteAccount(string code)
        {
            var user = await _context.Users.Where(d=>d.Code==code).FirstOrDefaultAsync();
            var company = await _context.Companies.Where(d => d.Code == code).FirstOrDefaultAsync();

            if (company != null && user != null)
            {
                _context.Users.Remove(user);
                _context.Companies.Remove(company);
                //almecen
                //_context.Items.RemoveRange();
                await _context.SaveChangesAsync();
            }
        }

        public async Task<Company> getCompany(string code)
        {
            return await _context.Companies.Where(d => d.Code == code).FirstOrDefaultAsync();
        }

        public async Task<User> getUserByEmailPassword(string email, string password)
        {
            return await _context.Users.Where(d => d.Email == email && d.Password == password).FirstOrDefaultAsync();
        }
    }
}
