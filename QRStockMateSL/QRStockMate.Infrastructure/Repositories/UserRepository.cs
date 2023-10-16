using Microsoft.EntityFrameworkCore;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.Infrastructure.Data;


namespace QRStockMate.Infrastructure.Repositories
{
    public class UserRepository : BaseRepository<User>, IUserRepository
    {

        private readonly ApplicationDbContext _context;
        private readonly IStorageRepository _contextStorage;

        public UserRepository(ApplicationDbContext context, IStorageRepository storage) : base(context)
        {
            _context = context;
           _contextStorage = storage;
        }

        public async Task DeleteAccount(string code)
        {
            var users = await _context.Users.Where(d=>d.Code==code).ToListAsync();
            var company = await _context.Companies.Where(d => d.Code == code).FirstOrDefaultAsync();

            if (company != null && users != null)
            {
                //Borrado Usuarios
                foreach (var user in users)
                {
                     await _contextStorage.DeleteImage(user.Url);
                }

                _context.Users.RemoveRange(users);


                //Borrado de Compañia
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

        public async Task<User> getDirectorByCode(string code)
        {
            return await _context.Users.Where(d=>d.Code==code && d.Role==RoleUser.Director).FirstOrDefaultAsync();
        }
    }
}
