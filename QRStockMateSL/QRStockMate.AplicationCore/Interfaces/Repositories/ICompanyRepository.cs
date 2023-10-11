using QRStockMate.AplicationCore.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QRStockMate.AplicationCore.Interfaces.Repositories
{
    public interface ICompanyRepository: IBaseRepository<Company>
    {
        //almacenes de empresa
        public Task<IEnumerable<Company>> getWarehouses(string code);
        //empleados de empresa
        public Task<IEnumerable<User>> getEmployees(string code);
    }
}
