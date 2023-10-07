using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Service;
using QRStockMate.Model;

namespace QRStockMate.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;
        private readonly IMapper _mapper;
        public UserController(IUserService userService, IMapper mapper)
        {
            _userService = userService;
            _mapper = mapper;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserModel>>> Get()
        {
            try
            {
                var users = await _userService.GetAll();

                if (users is null) return NotFound();//404

                return Ok(_mapper.Map<IEnumerable<User>, IEnumerable<UserModel>>(users)); //200
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] UserModel value)
        {

            try
            {
                var user = _mapper.Map<UserModel, User>(value);

                await _userService.Create(user);

                return CreatedAtAction("Get", new { id = value.Id }, value);
            }
            catch (Exception e)
            {

                return BadRequest(e.Message);//400
            }
        }

        [HttpPut]
        public async Task<ActionResult<UserModel>> Put([FromBody] UserModel model)
        {
            try
            {
                var user = _mapper.Map<UserModel, User>(model);

                if (user is null) return NotFound();//404

                await _userService.Update(user);

                return NoContent(); //202
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

    }
}
