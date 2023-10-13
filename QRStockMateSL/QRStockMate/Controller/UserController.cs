using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
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
        private readonly ICompanyService _companyService;
        private readonly IStorageService _context_storage;
        private readonly IMapper _mapper;
        public UserController(IUserService userService,IStorageService storageService, IMapper mapper, ICompanyService companyService)
        {
            _userService = userService;
            _context_storage = storageService;
            _mapper = mapper;
            _companyService = companyService;
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

                //await _context_storage.DeleteImage(user.Url);
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
        [HttpPost("SignIn")]
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
        [HttpPost("SignUp")]
        public async Task<IActionResult> Registro([FromBody] RegistrationModel model)
        {
            try
            {
                var user =model.User;
                var company = _mapper.Map<CompanyModel, Company>(model.Company);

                var userE = await _userService.getUserByEmailPassword(user.Email, Utility.Utility.EncriptarClave(user.Password));

                if (userE != null) { return Conflict(); }//409

                //RECIBIR LOS DATOS DEL FORMULARIO  ->>>>>>>> IMAGEN PREDETERMINADA
                // Stream image_stream = image.OpenReadStream();
                // string urlimagen = await _context_storage.UploadImage(image_stream, image.FileName);


                user.Password = Utility.Utility.EncriptarClave(user.Password);
                //  user.Url = urlimagen;

                if (user.Code.Length == 0) {
                    user.Code = Utility.Utility.GenerateCode();
                    user.Role = RoleUser.Director;

                    //Al ser director se crea la empresa aqui
                    company.Code = user.Code;
                    await _companyService.Create(company);

                }else
                {
                    user.Role = RoleUser.User;
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

        //Funciones Especiales
        [HttpPost("Company")]
        public async Task<ActionResult<Company>> GetCompanyByUser([FromBody]UserModel user)
        {
            try
            {
                var company = await _userService.getCompany(user.Code);

                return Ok(_mapper.Map<Company, CompanyModel>(company));
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpDelete("DeleteAccount")]
        public async Task<ActionResult<Company>> DeleteAccount([FromBody] UserModel user)
        {
            try
            {
                
                var userEntity = _mapper.Map<UserModel, User>(user);
                //await _context_storage.DeleteImage(user.Url);


                await _userService.DeleteAccount(userEntity.Code);

                return NoContent();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPost("UpdateImage")]
        public async Task<IActionResult> UpdateImage([FromForm] int userId,[FromForm] IFormFile image)
        {
            try
            {

                var user = await _userService.GetById(userId);      
                if (user == null)return NotFound(); 

                await _context_storage.DeleteImage(user.Url);

                Stream image_stream = image.OpenReadStream();
                string urlimagen = await _context_storage.UploadImage(image_stream, image.FileName);

                user.Url = urlimagen;

                await _userService.Update(user);
                return Ok();
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);
            }
        }

    }


    
}
