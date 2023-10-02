
using QRStockMate.AplicationCore.Entities;

namespace QRStockMate.AplicationCore.Interfaces.Service
{
    public interface IUserService:IBaseService<User>
    {
        public Task<IEnumerable<User>> getEmployees(string code);
        public Task<IEnumerable<User>> getWarehouses();
        public Task<IEnumerable<User>> getCompany(string code);
    }
}
