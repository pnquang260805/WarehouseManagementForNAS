import React, { useEffect, useState } from "react";

export default function ProductPage() {
    const API_BASE = "http://backend:8080";
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [provider, setProvider] = useState(null);

    // pagination / filter
    const [query, setQuery] = useState("");
    const [page, setPage] = useState(1);
    const PAGE_SIZE = 10;

    // modal / form
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editing, setEditing] = useState(null); // product being edited or null
    const emptyForm = {
        productName: "",
        description: "",
        imgUrl: "",
        pricePerUnit: 0,
        providerId: null,
        purchaseOrdersId: null,
    };
    const [form, setForm] = useState(emptyForm);

    useEffect(() => {
        fetchProducts();
    }, []);

    // Fetch API
    async function fetchProducts() {
        setLoading(true);
        setError(null);
        try {
            const url = query
                ? `${API_BASE}/products/search?name=${encodeURIComponent(query)}`
                : `${API_BASE}/products`;
            const res = await fetch(url);
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();

            // Lấy tên nhà cung cấp cho từng sản phẩm
            const productsWithProvider = await Promise.all(
                (Array.isArray(data) ? data : data.content || []).map(async (product) => {
                    const providerName = product.providerId ? await handleProvider(product.providerId) : "Unknown";
                    return { ...product, providerName };
                })
            );

            setProducts(productsWithProvider);
        } catch (err) {
            setError(err.message);
            setProducts([]);
        } finally {
            setLoading(false);
        }
    }

    function openAdd() {
        setEditing(null);
        setForm(emptyForm);
        setIsModalOpen(true);
    }

    function openEdit(p) {
        setEditing(p); // Đặt editing khác null
        setForm({
            productName: p.productName || "",
            description: p.description || "",
            imgUrl: p.imgUrl || "",
            pricePerUnit: p.pricePerUnit || 0,
            providerId: p.providerId ?? null,
            purchaseOrdersId: p.purchaseOrdersId ?? null,
        });
        setIsModalOpen(true);
    }

    function closeModal() {
        setIsModalOpen(false);
        setEditing(null);
    }

    async function handleSave(e) {
        e.preventDefault();
        // basic validation
        if (!form.productName) return alert("Tên sản phẩm không được để trống");

        try {
            if (editing) {
                // PUT /products/{id}
                const res = await fetch(`${API_BASE}/products/${editing.id}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ ...editing, ...form }),
                });
                if (!res.ok) throw new Error(`HTTP ${res.status}`);
                const updated = await res.json();
                setProducts((prev) => prev.map((p) => (p.id === updated.id ? updated : p)));
            } else {
                // POST /products
                const res = await fetch(`${API_BASE}/products`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(form),
                });
                if (!res.ok) throw new Error(`HTTP ${res.status}`);
                const created = await res.json();
                setProducts((prev) => [created, ...prev]);
            }
            closeModal();
        } catch (err) {
            alert("Lưu thất bại: " + err.message);
        }
    }

    async function handleDelete(id) {
        if (!window.confirm("Xác nhận xóa sản phẩm này?")) return;
        try {
            const res = await fetch(`${API_BASE}/products/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setProducts((prev) => prev.filter((p) => p.id !== id));
        } catch (err) {
            alert("Xóa thất bại: " + err.message);
        }
    }

    async function handleProvider(id) {
        try {
            const res = await fetch(`${API_BASE}/providers/${id}`, { method: "GET" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            return data.name; // Trả về tên nhà cung cấp
        } catch (err) {
            console.error(err.message);
            return "Unknown"; // Trả về giá trị mặc định nếu lỗi
        }
    }

    // filtered & paginated view
    const filtered = products.filter((p) => {
        if (!query) return true;
        const q = query.toLowerCase();
        return (
            String(p.id).includes(q) ||
            (p.productName || "").toLowerCase().includes(q) ||
            (p.description || "").toLowerCase().includes(q)
        );
    });
    const totalPages = Math.max(1, Math.ceil(filtered.length / PAGE_SIZE));
    const pageItems = filtered.slice((page - 1) * PAGE_SIZE, page * PAGE_SIZE);

    return (
        <div className="p-6 font-sans">
            <header className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold">Quản lý sản phẩm - Warehouse</h1>
                <div className="flex gap-3">
                    <input
                        value={query}
                        onChange={(e) => setQuery(e.target.value)}
                        placeholder="Tìm theo tên"
                        className="px-3 py-2 border rounded"
                    />
                    <button onClick={openAdd} className="px-4 py-2 bg-blue-600 text-white rounded">
                        Thêm sản phẩm
                    </button>
                    <button onClick={fetchProducts} className="px-4 py-2 border rounded">
                        Làm mới
                    </button>
                </div>
            </header>

            <main>
                {loading ? (
                    <div>Đang tải...</div>
                ) : error ? (
                    <div className="text-red-600">Lỗi: {error}</div>
                ) : (
                    <div className="overflow-x-auto bg-white rounded shadow">
                        <table className="min-w-full text-left">
                            <thead className="bg-gray-100">
                                <tr>
                                    <th className="p-3">ID</th>
                                    <th className="p-3">Ảnh</th>
                                    <th className="p-3">Tên</th>
                                    <th className="p-3">Mô tả</th>
                                    <th className="p-3">Giá</th>
                                    <th className="p-3">Nhà cung cấp</th>
                                    <th className="p-3">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                {pageItems.length === 0 && (
                                    <tr>
                                        <td colSpan={8} className="p-4 text-center text-gray-500">
                                            Không có sản phẩm
                                        </td>
                                    </tr>
                                )}
                                {pageItems.map((p) => (
                                    <tr key={p.id} className="border-t">
                                        <td className="p-3 align-top">{p.id}</td>
                                        <td className="p-3 align-top">
                                            {p.imgUrl ? (
                                                <img src={p.imgUrl} alt={p.productName} className="w-20 h-20 object-cover rounded" />
                                            ) : (
                                                <div className="w-20 h-20 flex items-center justify-center bg-gray-50 text-sm text-gray-400 rounded">
                                                    No Image
                                                </div>
                                            )}
                                        </td>
                                        <td className="p-3 align-top">{p.productName}</td>
                                        <td className="p-3 align-top">{p.description}</td>
                                        <td className="p-3 align-top">{p.pricePerUnit}</td>
                                        <td className="p-3 align-top">{p.providerName}</td>
                                        <td className="p-3 align-top">
                                            <div className="flex gap-2">
                                                <button onClick={() => openEdit(p)} className="px-3 py-1 border rounded">
                                                    Sửa
                                                </button>
                                                <button onClick={() => handleDelete(p.id)} className="px-3 py-1 border rounded text-red-600">
                                                    Xóa
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}

                {/* Pagination */}
                <div className="flex items-center justify-between mt-4">
                    <div>
                        Hiển thị {filtered.length} kết quả • Trang {page} / {totalPages}
                    </div>
                    <div className="flex gap-2">
                        <button onClick={() => setPage((p) => Math.max(1, p - 1))} className="px-3 py-1 border rounded">
                            Prev
                        </button>
                        <button onClick={() => setPage((p) => Math.min(totalPages, p + 1))} className="px-3 py-1 border rounded">
                            Next
                        </button>
                    </div>
                </div>
            </main>

            {/* Modal form */}
            {isModalOpen && (
                <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4">
                    <div className="bg-white w-full max-w-2xl rounded shadow-lg p-6">
                        <h2 className="text-xl font-semibold mb-4">{editing ? "Sửa sản phẩm" : "Thêm sản phẩm"}</h2>
                        {/* gọi hàm handle save */}
                        <form onSubmit={handleSave} className="grid grid-cols-2 gap-4">
                            <div className="col-span-2">
                                <label className="block text-sm">Tên sản phẩm</label>
                                <input
                                    className="w-full px-3 py-2 border rounded"
                                    value={form.productName}
                                    onChange={(e) => setForm({ ...form, productName: e.target.value })}
                                />
                            </div>

                            <div className="col-span-2">
                                <label className="block text-sm">Mô tả</label>
                                <textarea
                                    className="w-full px-3 py-2 border rounded"
                                    value={form.description}
                                    onChange={(e) => setForm({ ...form, description: e.target.value })}
                                />
                            </div>

                            <div>
                                <label className="block text-sm">Ảnh (URL)</label>
                                <input
                                    className="w-full px-3 py-2 border rounded"
                                    value={form.imgUrl}
                                    onChange={(e) => setForm({ ...form, imgUrl: e.target.value })}
                                />
                            </div>

                            <div>
                                <label className="block text-sm">Giá / đơn vị</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    className="w-full px-3 py-2 border rounded"
                                    value={form.pricePerUnit}
                                    onChange={(e) => setForm({ ...form, pricePerUnit: parseFloat(e.target.value || 0) })}
                                />
                            </div>

                            <div>
                                <label className="block text-sm">Provider ID</label>
                                <input
                                    className="w-full px-3 py-2 border rounded"
                                    value={form.providerId ?? ""}
                                    onChange={(e) => setForm({ ...form, providerId: e.target.value ? Number(e.target.value) : null })}
                                />
                            </div>

                            <div>
                                <label className="block text-sm">PurchaseOrder ID</label>
                                <input
                                    className="w-full px-3 py-2 border rounded"
                                    value={form.purchaseOrdersId ?? ""}
                                    onChange={(e) => setForm({ ...form, purchaseOrdersId: e.target.value ? Number(e.target.value) : null })}
                                />
                            </div>

                            <div className="col-span-2 flex justify-end gap-2 mt-2">
                                <button type="button" onClick={closeModal} className="px-4 py-2 border rounded">
                                    Hủy
                                </button>
                                <button type="submit" className="px-4 py-2 bg-green-600 text-white rounded">
                                    Lưu
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}
