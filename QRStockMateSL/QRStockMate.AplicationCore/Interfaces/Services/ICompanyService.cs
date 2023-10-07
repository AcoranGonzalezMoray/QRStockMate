using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QRStockMate.AplicationCore.Interfaces.Services
{
    public interface ICompanyService:IBaseService<Company>
    {
        public Task<IEnumerable<User>> getEmployees(string code);

        public Task<IEnumerable<Company>> getWarehouses(string code);
    }
}
