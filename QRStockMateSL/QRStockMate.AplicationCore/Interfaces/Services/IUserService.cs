
using QRStockMate.AplicationCore.Entities;

namespace QRStockMate.AplicationCore.Interfaces.Service
{
    public interface IUserService:IBaseService<User>
    {
        public Task<IEnumerable<User>> getEmployees();
        public Task<IEnumerable<User>> getWarehouses();
        public Task<IEnumerable<User>> getCompany();
    }
}
