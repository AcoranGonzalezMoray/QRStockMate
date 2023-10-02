using AutoMapper;
using QRStockMate.AplicationCore.Entities;
using QRStockMate.Model;

namespace CleanArquitecture.Api.Mapping
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            //User
            CreateMap<User, UserModel>();
            CreateMap<UserModel, User>();
        }
    }
}
