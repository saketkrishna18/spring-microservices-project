import { useState } from "react";
import api from "../api/axios";
import Navbar from "../components/Navbar";

function AddProduct() {
    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [quantity, setQuantity] = useState("");

    const addProduct = async () => {
        try {
            await api.post("/products", {
                name: name,
                price: price,
                quantity: quantity
            });

            alert("Product added successfully 🚀");

            setName("");
            setPrice("");
            setQuantity("");

        } catch (err) {
            console.log(err);
            alert("Failed to add product");
        }
    };

    return (
        <div>
            <Navbar />

            <h2>Add Product (ADMIN)</h2>

            <input
                type="text"
                placeholder="Product Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />

            <br /><br />

            <input
                type="number"
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
            />

            <br /><br />

            <input
                type="number"
                placeholder="Quantity"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
            />

            <br /><br />

            <button onClick={addProduct}>
                Add Product
            </button>
        </div>
    );
}

export default AddProduct;