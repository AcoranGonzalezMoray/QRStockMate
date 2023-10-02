using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.AplicationCore.Interfaces.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QRStockMate.Services
{
    public class BaseService<TEntity>:IBaseService<TEntity> where TEntity : class
    {
        private readonly IBaseRepository<TEntity> _tRepository;
        public BaseService(IBaseRepository<TEntity> _Repository)
        {
            _tRepository = _Repository;
        }
        public async Task<TEntity> GetById(int id)
        {
          return await _tRepository.GetById(id);
        }

        public async Task<IEnumerable<TEntity>> GetAll()
        {
            return await _tRepository.GetAll();
        }

        public async void Delete(TEntity entity)
        {
            _tRepository.Delete(entity);
        }

        public async void DeleteRange(IEnumerable<TEntity> entities)
        {
            _tRepository.DeleteRange(entities);
        }

        public async Task Update(TEntity entity)
        {
            await _tRepository.Update(entity);
        }

        public async Task UpdateRange(IEnumerable<TEntity> entities)
        {
            await _tRepository.UpdateRange(entities); 
        }

        public async void Create(TEntity entity)
        {
             _tRepository.Create(entity);
        }
    }
}
