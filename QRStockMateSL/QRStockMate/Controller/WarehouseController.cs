using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Repositories;
using QRStockMate.AplicationCore.Interfaces.Services;
using QRStockMate.Model;
using QRStockMate.Services;

namespace QRStockMate.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class WarehouseController : ControllerBase
    {
        private readonly IWarehouseService _warehouseService;
        private readonly IStorageService _context_storage;
        private readonly IMapper _mapper;

        public WarehouseController(IWarehouseService warehouseService, IMapper mapper, IStorageService context_storage)
        {
            _warehouseService = warehouseService;
            _context_storage = context_storage;
            _mapper = mapper;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<WarehouseModel>>> Get()
        {
            try
            {
                var warehouses = await _warehouseService.GetAll();

                if (warehouses is null) return NotFound();//404

                return Ok(_mapper.Map<IEnumerable<Warehouse>, IEnumerable<WarehouseModel>>(warehouses)); //200
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] WarehouseModel value)
        {

            try
            {
                var warehouse = _mapper.Map<WarehouseModel, Warehouse>(value);

                await _warehouseService.Create(warehouse);

                return CreatedAtAction("Get", new { id = value.Id }, value);
            }
            catch (Exception e)
            {

                return BadRequest(e.Message);//400
            }
        }

        [HttpPut]
        public async Task<ActionResult<UserModel>> Put([FromBody] WarehouseModel model)
        {
            try
            {
                var warehouse = _mapper.Map<WarehouseModel, Warehouse>(model);

                if (warehouse is null) return NotFound();//404

                await _warehouseService.Update(warehouse);

                return NoContent(); //204
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }
        
        [HttpDelete]
        public async Task<IActionResult> Delete([FromBody] WarehouseModel model)
        {
            try
            {
                var warehouse = _mapper.Map<WarehouseModel, Warehouse>(model);

                if (warehouse is null) return NotFound();//404

                //await _context_storage.DeleteImage(warehouse.Url);
                await _warehouseService.Delete(warehouse);

                return NoContent(); //204
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpPost("UpdateImage")]
        public async Task<IActionResult> UpdateImage([FromForm] int warehouseId, [FromForm] IFormFile image)
        {
            try
            {

                var warehouse = await _warehouseService.GetById(warehouseId);
                if (warehouse == null) return NotFound();

                if (Uri.IsWellFormedUriString(warehouse.Url, UriKind.Absolute))
                {
                    // Es una URL válida, puedes proceder con la eliminación
                    await _context_storage.DeleteImage(warehouse.Url);
                }

                Stream image_stream = image.OpenReadStream();
                string urlimagen = await _context_storage.UploadImage(image_stream, image.FileName);

                warehouse.Url = urlimagen;

                await _warehouseService.Update(warehouse);
                return Ok();
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);
            }
        }

    }
}

