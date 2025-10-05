import React, { useEffect, useState } from "react";

export default function AddProviderPage() {
    const API_BASE = "/api";
    const [name, setName] = useState("");
    const [headOfficeLocation, setHeadOfficeLocation] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [providers, setProviders] = useState([]);

    useEffect(() => {
        fetchProviders();
    }, []);

    async function fetchProviders() {
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/providers`);
            if (!res.ok) throw new Error(`Failed to fetch providers: ${res.status}`);
            const data = await res.json();
            // Assuming API returns list of ProviderResponse
            setProviders(Array.isArray(data) ? data : data.content || []);
        } catch (err) {
            setError(err.message);
        }
    }

    function validate() {
        if (!name.trim()) return "Tên nhà cung cấp không được để trống";
        if (!headOfficeLocation.trim()) return "Vị trí trụ sở không được để trống";
        return null;
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError(null);
        setSuccess(null);
        const v = validate();
        if (v) {
            setError(v);
            return;
        }

        setLoading(true);
        try {
            const body = { name: name.trim(), headOfficeLocation: headOfficeLocation.trim() };
            const res = await fetch(API_BASE, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body),
            });
            if (!res.ok) {
                const txt = await res.text();
                throw new Error(`Server error ${res.status}: ${txt}`);
            }
            const created = await res.json();
            setProviders((p) => [created, ...p]);
            setName("");
            setHeadOfficeLocation("");
            setSuccess("Tạo nhà cung cấp thành công.");
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="min-h-screen bg-gray-50 p-6">
            <div className="max-w-4xl mx-auto">
                <h1 className="text-2xl font-semibold mb-4">Thêm nhà cung cấp</h1>

                <form onSubmit={handleSubmit} className="bg-white shadow rounded p-6 mb-6">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label className="block text-sm font-medium mb-1">Tên nhà cung cấp</label>
                            <input
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-200"
                                placeholder="Ví dụ: Công ty ABC"
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-medium mb-1">Vị trí trụ sở</label>
                            <input
                                value={headOfficeLocation}
                                onChange={(e) => setHeadOfficeLocation(e.target.value)}
                                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-200"
                                placeholder="Ví dụ: Hà Nội"
                            />
                        </div>
                    </div>

                    <div className="mt-4 flex items-center gap-3">
                        <button
                            type="submit"
                            disabled={loading}
                            className="bg-indigo-600 text-white px-4 py-2 rounded shadow hover:bg-indigo-700 disabled:opacity-60"
                        >
                            {loading ? "Đang tạo..." : "Tạo nhà cung cấp"}
                        </button>

                        <button
                            type="button"
                            onClick={() => { setName(""); setHeadOfficeLocation(""); setError(null); setSuccess(null); }}
                            className="px-3 py-2 rounded border"
                        >
                            Reset
                        </button>

                        <button
                            type="button"
                            onClick={fetchProviders}
                            className="ml-auto px-3 py-2 rounded border"
                        >
                            Reload danh sách
                        </button>
                    </div>

                    {error && <div className="mt-4 text-red-600">{error}</div>}
                    {success && <div className="mt-4 text-green-600">{success}</div>}
                </form>

                <div className="bg-white shadow rounded p-6">
                    <div className="flex items-center justify-between mb-4">
                        <h2 className="text-lg font-medium">Danh sách nhà cung cấp</h2>
                        <div className="text-sm text-gray-500">{providers.length} kết quả</div>
                    </div>

                    {providers.length === 0 ? (
                        <div className="text-gray-500">Chưa có nhà cung cấp nào. Nhấn "Reload danh sách" để thử lại.</div>
                    ) : (
                        <div className="overflow-x-auto">
                            <table className="min-w-full text-sm">
                                <thead>
                                    <tr className="text-left text-gray-600 border-b">
                                        <th className="py-2 px-3">ID</th>
                                        <th className="py-2 px-3">Tên</th>
                                        <th className="py-2 px-3">Trụ sở</th>
                                        <th className="py-2 px-3">Số sản phẩm</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {providers.map((p) => (
                                        <tr key={p.id} className="border-b hover:bg-gray-50">
                                            <td className="py-2 px-3">{p.id}</td>
                                            <td className="py-2 px-3">{p.name}</td>
                                            <td className="py-2 px-3">{p.headOfficeLocation}</td>
                                            <td className="py-2 px-3">{Array.isArray(p.productsId) ? p.productsId.length : 0}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}

                    {error && <div className="mt-4 text-red-600">{error}</div>}
                </div>
            </div>
        </div>
    );
}
