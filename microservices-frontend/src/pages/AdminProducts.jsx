import { useEffect, useState } from "react";
import api from "../api/axios";
import Navbar from "../components/Navbar";

function AdminProducts() {

    const [products, setProducts] = useState([]);

    const loadProducts = async () => {
        const res = await api.get("/products");
        setProducts(res.data);
    };

    const deleteProduct = async (id) => {
        try {
            await api.delete(`/products/${id}`);
            alert("Deleted");
            loadProducts();
        } catch (err) {
            console.log(err);
            alert("Delete failed");
        }
    };

    const updateProduct = async (id) => {
        const name = prompt("Enter new name");
        const newPrice = prompt("Enter new price");
        const qty = prompt("Enter new Quantity");

        try {
            await api.put(`/products/${id}`, {
                price: newPrice, name:name,quantity:qty
            });

            alert("Updated");
            loadProducts();
        } catch (err) {
            console.log(err);
            alert("Update failed");
        }
    };

    useEffect(() => {
        loadProducts();
    }, []);
    const cellStyle = {
        border: "1px solid #ddd",
        padding: "10px"
    };

    const editBtn = {
        marginRight: "10px",
        padding: "5px 10px",
        backgroundColor: "orange",
        border: "none",
        cursor: "pointer"
    };

    const deleteBtn = {
        padding: "5px 10px",
        backgroundColor: "red",
        color: "white",
        border: "none",
        cursor: "pointer"
    };
    return (
        <div>
            <Navbar />

            <h2 style={{ textAlign: "center" }}>Admin Products Dashboard</h2>

            <table style={{
                width: "90%",
                margin: "20px auto",
                borderCollapse: "collapse"
            }}>
                <thead>
                <tr style={{ background: "#333", color: "white" }}>
                    <th style={cellStyle}>ID</th>
                    <th style={cellStyle}>Name</th>
                    <th style={cellStyle}>Price</th>
                    <th style={cellStyle}>Quantity</th>
                    <th style={cellStyle}>Actions</th>
                </tr>
                </thead>

                <tbody>
                {products.map((p) => (
                    <tr key={p.id} style={{ textAlign: "center" }}>

                        <td style={cellStyle}>{p.id}</td>
                        <td style={cellStyle}>{p.name}</td>
                        <td style={cellStyle}>{p.price}</td>
                        <td style={cellStyle}>{p.quantity}</td>

                        <td style={cellStyle}>

                            <button
                                onClick={() => updateProduct(p.id)}
                                style={editBtn}
                            >
                                Edit
                            </button>

                            <button
                                onClick={() => deleteProduct(p.id)}
                                style={deleteBtn}
                            >
                                Delete
                            </button>

                        </td>

                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminProducts;