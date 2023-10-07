﻿using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using QRStockMate.Utility;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.Model;
using QRStockMate.AplicationCore.Interfaces.Services;

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

        //FUNCIONES BASICAS

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

        [HttpDelete]
        public async Task<ActionResult<UserModel>> Delete([FromBody] UserModel model)
        {
            try
            {
                var user = _mapper.Map<UserModel, User>(model);

                if (user is null) return NotFound();//404

                await _userService.Delete(user);

                return NoContent(); //202
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }


        //FUNCIONES DE LOGIN
        [AllowAnonymous]
        [HttpPost("IniciarSesion")]
        public async Task<IActionResult> IniciarSesion([FromForm] string email, [FromForm] string password)
        {
            try
            {
                var user = await _userService.getUserByEmailPassword(email, Utility.Utility.EncriptarClave(password));
                if (user == null) { return NotFound(); }//404
                //var token = _context_jwt.GenToken(user.Email, user.Password);


                var response = new
                {
                    User = user,
                    //Token = token
                };


                return Ok(response);//200
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);//400
            }
        }

        [AllowAnonymous]
        [HttpPost("Registro")]
        public async Task<IActionResult> Registro(UserModel user)
        {
            try
            {
                var userE = await _userService.getUserByEmailPassword(user.Email, Utility.Utility.EncriptarClave(user.Password));

                if (userE != null) { return Conflict(); }//409

                user.Password = Utility.Utility.EncriptarClave(user.Password);

                if (user.Code.Length == 0) {
                    user.Code = Utility.Utility.GenerateCode();
                    user.Role = RoleUser.Director;

                    //Al ser director se crea la empresa aqui
                }
               
                var userEntity = _mapper.Map<UserModel,User>(user);
                await _userService.Create(userEntity);

                return CreatedAtAction("Get", new { id = user.Id }, user);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


      
        
    }
}
