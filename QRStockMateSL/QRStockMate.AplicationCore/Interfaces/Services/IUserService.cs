
using QRStockMate.AplicationCore.Entities;

namespace QRStockMate.AplicationCore.Interfaces.Service
{
    public interface IUserService:IBaseService<User>
    {
        public Task<IEnumerable<User>> getCompany(string code);
        public Task<User> getUserByEmailPassword(string email, string password);
    }
}
