import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate} from "react-router-dom";

function MyOrders() {

    const [orders,
        setOrders] =
        useState([]);

    useEffect(() => {

        const token =
            localStorage.getItem(
                "token"
            );

        if (!token) {

            alert(
                "Please login first"
            );

            navigate("/");

            return;
        }

        getOrders();

    }, []);
    const navigate =
        useNavigate();

    const getOrders =
        async () => {

            try {

                const token =
                    localStorage
                        .getItem(
                            "token"
                        );

                const response =
                    await axios.get(
                        "http://localhost:9090/orders/my-orders",
                        {
                            headers: {
                                Authorization:
                                    `Bearer ${token}`
                            }
                        }
                    );

                setOrders(
                    response.data
                );

            } catch (error) {

                console.log(
                    error
                );

                alert(
                    "Failed to load orders"
                );
            }
        };

    return (

        <div
            style={{
                padding:
                    "20px"
            }}
        >

            <h1>
                My Orders
            </h1>
            <div
                style={{
                    marginBottom:
                        "20px"
                }}
            >

                <button
                    onClick={() =>
                        navigate(
                            "/products"
                        )
                    }
                >
                    Products
                </button>

                <button
                    onClick={() => {

                        localStorage.removeItem(
                            "token"
                        );

                        navigate(
                            "/"
                        );

                    }}

                    style={{
                        marginLeft:
                            "10px"
                    }}
                >
                    Logout
                </button>

            </div>
            {
                orders.map(
                    (order) => (

                        <div
                            key={
                                order.id
                            }

                            style={{
                                border:
                                    "1px solid black",
                                padding:
                                    "10px",
                                margin:
                                    "10px"
                            }}
                        >

                            <h3>
                                Order Id:
                                {
                                    order.id
                                }
                            </h3>

                            <p>
                                Product Name:
                                {
                                    order.productName
                                }
                            </p>

                            <p>
                                Quantity:
                                {
                                    order.quantity
                                }
                            </p>

                            <p>
                                Price:
                                {
                                    order.totalPrice
                                }
                            </p>

                        </div>
                    )
                )
            }

        </div>
    );
}

export default MyOrders;