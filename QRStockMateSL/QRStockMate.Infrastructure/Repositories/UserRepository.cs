using Microsoft.EntityFrameworkCore;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.Infrastructure.Data;

namespace QRStockMate.Infrastructure.Repositories
{
    public class UserRepository : BaseRepository<User>, IUserRepository
    {

        private readonly ApplicationDbContext _context;
        private readonly DbSet<User> _entities;

        public UserRepository(ApplicationDbContext context) : base(context)
        {
            _context = context;
            _entities = _context.Set<User>();
        }

        public Task<IEnumerable<User>> getCompany()
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<User>> getEmployees()
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<User>> getWarehouses()
        {
            throw new NotImplementedException();
        }
    }
}
