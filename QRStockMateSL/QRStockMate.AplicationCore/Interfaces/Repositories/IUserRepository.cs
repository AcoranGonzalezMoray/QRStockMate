
using QRStockMate.AplicationCore.Entities;

namespace QRStockMate.AplicationCore.Interfaces.Repositories
{
    public interface IUserRepository:IBaseRepository<User>
    {
        public Task<IEnumerable<User>> getCompany(string  code);
        public Task<User> getUserByEmailPassword(string email, string password);
    }
}
