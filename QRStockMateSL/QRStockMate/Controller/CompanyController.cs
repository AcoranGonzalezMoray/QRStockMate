﻿using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.AplicationCore.Interfaces.Service;
using QRStockMate.AplicationCore.Interfaces.Services;
using QRStockMate.Model;

namespace QRStockMate.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class CompanyController : ControllerBase
    {
        private readonly ICompanyService _companyService;
        private readonly IMapper _mapper;

        public CompanyController(ICompanyService companyService, IMapper mapper)
        {
            _companyService = companyService;
            _mapper = mapper;
        }


        //---------------- FUNCIONES BASICAS (GET, POST, PUT, DELETE) ----------------

        [HttpGet]
        public async Task<ActionResult<IEnumerable<CompanyModel>>> Get()
        {
            try
            {
                Console.WriteLine("Diosss bro llegué");
                var companies = await _companyService.GetAll();

                if (companies is null) return NotFound();//404

                return Ok(_mapper.Map<IEnumerable<Company>, IEnumerable<CompanyModel>>(companies)); //200
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] CompanyModel value)
        {

            try
            {
                var company = _mapper.Map<CompanyModel, Company>(value);

                await _companyService.Create(company);

                return CreatedAtAction("Get", new { id = value.Id }, value);    //Id de Company
            }
            catch (Exception e)
            {

                return BadRequest(e.Message);//400
            }
        }

        [HttpPut]
        public async Task<ActionResult<CompanyModel>> Put([FromBody] CompanyModel model)
        {
            try
            {
                var company = _mapper.Map<CompanyModel, Company>(model);

                if (company is null) return NotFound();//404

                await _companyService.Update(company);

                return NoContent(); //202
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }

        [HttpDelete]
        public async Task<ActionResult<CompanyModel>> Delete([FromBody] CompanyModel model)
        {
            try
            {
                var company = _mapper.Map<CompanyModel, Company>(model);

                if (company is null) return NotFound();//404

                await _companyService.Delete(company);

                return NoContent(); //202
            }
            catch (Exception ex)
            {

                return BadRequest(ex.Message);//400
            }
        }
    }
}
