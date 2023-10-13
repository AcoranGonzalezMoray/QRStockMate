using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace QRStockMate.Infrastructure.Migrations
{
    /// <inheritdoc />
    public partial class v2_merge : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "warehouseId",
                table: "Items",
                newName: "WarehouseId");

            migrationBuilder.RenameColumn(
                name: "url",
                table: "Items",
                newName: "Url");

            migrationBuilder.RenameColumn(
                name: "stock",
                table: "Items",
                newName: "Stock");

            migrationBuilder.RenameColumn(
                name: "location",
                table: "Items",
                newName: "Location");

            migrationBuilder.AddColumn<DateTime>(
                name: "Created",
                table: "TransactionsHistory",
                type: "datetime2",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Created",
                table: "TransactionsHistory");

            migrationBuilder.RenameColumn(
                name: "WarehouseId",
                table: "Items",
                newName: "warehouseId");

            migrationBuilder.RenameColumn(
                name: "Url",
                table: "Items",
                newName: "url");

            migrationBuilder.RenameColumn(
                name: "Stock",
                table: "Items",
                newName: "stock");

            migrationBuilder.RenameColumn(
                name: "Location",
                table: "Items",
                newName: "location");
        }
    }
}
