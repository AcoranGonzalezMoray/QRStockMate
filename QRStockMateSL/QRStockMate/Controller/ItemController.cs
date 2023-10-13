using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Services;
using QRStockMate.Model;
using QRStockMate.Services;

namespace QRStockMate.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class ItemController : ControllerBase
    {
        private readonly IItemService _itemService;
        private readonly IMapper _mapper;

        public ItemController(IItemService itemService, IMapper mapper)
        {
            _itemService = itemService;
            _mapper = mapper;
        }

        //------------------------ Sentencias ------------------------------

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ItemModel>>> Get()
        {
            try
            {
                var items = await _itemService.GetAll();

                if (items is null) return NotFound();//404

                return Ok(_mapper.Map<IEnumerable<Item>, IEnumerable<ItemModel>>(items)); //200
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] ItemModel value)
        {
            try
            {
                var item = _mapper.Map<ItemModel, Item>(value);

                await _itemService.Create(item);

                return CreatedAtAction("Get", new { id = item.Id }, item);   
            }
            catch (Exception e)
            {

                return BadRequest(e.Message);//400
            }
        }

        [HttpPut]
        public async Task<ActionResult<ItemModel>> Put([FromBody] ItemModel model)
        {
            try
            {
                var item = _mapper.Map<ItemModel, Item>(model);

                if (item is null) return NotFound();//404

                await _itemService.Update(item);

                return NoContent(); //202
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpDelete]
        public async Task<ActionResult<ItemModel>> Delete([FromBody] ItemModel model)
        {
            try
            {
                var item = _mapper.Map<ItemModel, Item>(model);

                if (item is null) return NotFound();//404

                await _itemService.Delete(item);

                return NoContent(); //202
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        //Obtener Items por nombre
        [HttpGet("Search/{name}")]
        public async Task<ActionResult<IEnumerable<ItemModel>>> GetItemsByName(string name)
        {
            try
            {
                var items = await _itemService.getItems(name);

                if (items is null) return NotFound();//404

                return Ok(_mapper.Map<IEnumerable<Item>, IEnumerable<ItemModel>>(items)); //200
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }
    }
}
