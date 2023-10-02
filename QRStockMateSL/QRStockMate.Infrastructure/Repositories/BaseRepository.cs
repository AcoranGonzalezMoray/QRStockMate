using Microsoft.EntityFrameworkCore;
using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.Infrastructure.Data;

namespace QRStockMate.Infrastructure.Repositories
{
    public class BaseRepository<TEntity> : IBaseRepository<TEntity> where TEntity : class
    {

        private readonly ApplicationDbContext _context;
        private readonly DbSet<TEntity> _entities;

        public BaseRepository(ApplicationDbContext context)
        {
            _context = context;
            _entities = _context.Set<TEntity>();
        }

        public void Create(TEntity entity)
        {
            throw new NotImplementedException();
        }

        public async  void DeleteById(TEntity entity)
        {
            _entities.Remove(entity);
            await _context.SaveChangesAsync();
        }

        public void DeleteRange(IEnumerable<TEntity> entities)
        {
            throw new NotImplementedException();
        }

        public async Task<IEnumerable<TEntity>> GetAll()
        {
            return await _entities.ToListAsync();
        }

        public Task<TEntity> GetById(int id)
        {
            throw new NotImplementedException();
        }

        public Task Update(TEntity entity)
        {
            throw new NotImplementedException();
        }

        public Task UpdateRange(IEnumerable<TEntity> entities)
        {
            throw new NotImplementedException();
        }
    }
}
