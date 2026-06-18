import { useEffect, useState } from "react";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import {Link} from "react-router-dom";

function Products() {

    const [products, setProducts] = useState([]);

    const loadProducts = async () => {
        try {
            const res = await api.get("/products");
            setProducts(res.data);
        } catch (err) {
            console.log(err);
            alert("Failed to load products");
        }
    };
    const placeOrder = async (productId) => {
        try {
            const res = await api.post("/orders", {
                productId: productId,
                quantity: 1
            });

            alert("Order placed successfully 🚀");

            console.log(res.data);

        } catch (err) {
            console.log(err);
            alert("Order failed");
        }
    };
    const cellStyle = {
        border: "1px solid #ddd",
        padding: "10px"
    };
    useEffect(() => {
        loadProducts();
    }, []);

    return (
        <div>
            <Navbar />




            <h2 style={{ textAlign: "center" }}>Products Details</h2>

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
                    <th style={cellStyle}>Available Quantity</th>
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

                            <button onClick={() => placeOrder(p.id)}>
                                Order Now
                            </button>

                        </td>

                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Products;