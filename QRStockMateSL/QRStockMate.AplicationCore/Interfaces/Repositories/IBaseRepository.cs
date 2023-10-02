using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QRStockMate.AplicationCore.Interfaces.Repositories
{
    public interface IBaseRepository<TEntity> where TEntity : class
    {
        Task<TEntity> GetById(int id);
        Task<IEnumerable<TEntity>> GetAll();
        void DeleteById(TEntity entity);
        void DeleteRange(IEnumerable<TEntity> entities);
        Task Update(TEntity entity);
        Task UpdateRange(IEnumerable<TEntity> entities);
        void Create(TEntity entity);
    }
}
