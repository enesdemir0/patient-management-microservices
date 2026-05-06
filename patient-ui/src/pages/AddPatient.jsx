import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../api/axios'

const FIELDS = [
  { name: 'name',           label: 'Full Name',        type: 'text',  placeholder: 'John Doe' },
  { name: 'email',          label: 'Email',             type: 'email', placeholder: 'john@example.com' },
  { name: 'address',        label: 'Address',           type: 'text',  placeholder: '123 Main St, City' },
  { name: 'dateOfBirth',    label: 'Date of Birth',     type: 'date',  placeholder: '' },
  { name: 'registeredDate', label: 'Registered Date',   type: 'date',  placeholder: '' },
]

export default function AddPatient() {
  const [form, setForm] = useState({
    name: '', email: '', address: '', dateOfBirth: '', registeredDate: '',
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  function handleChange(e) {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await api.post('/api/patients', form)
      navigate('/dashboard')
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create patient. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50">

      {/* Navbar */}
      <nav className="bg-white border-b border-gray-200 px-6 py-4 flex items-center gap-3">
        <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
          <svg className="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
              d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
          </svg>
        </div>
        <span className="font-bold text-gray-900 text-lg">HealthCare Portal</span>
      </nav>

      {/* Content */}
      <main className="max-w-2xl mx-auto px-6 py-8">
        <div className="mb-6 flex items-center gap-4">
          <Link to="/dashboard" className="text-gray-400 hover:text-gray-600 transition-colors text-sm">
            ← Back to patients
          </Link>
        </div>
        <h2 className="text-2xl font-bold text-gray-900 mb-6">Add New Patient</h2>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
          <form onSubmit={handleSubmit} className="space-y-5">
            {FIELDS.map(f => (
              <div key={f.name}>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  {f.label}
                </label>
                <input
                  type={f.type}
                  name={f.name}
                  required
                  value={form[f.name]}
                  onChange={handleChange}
                  placeholder={f.placeholder}
                  className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                />
              </div>
            ))}

            {error && (
              <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                {error}
              </div>
            )}

            <div className="flex gap-3 pt-2">
              <button
                type="submit"
                disabled={loading}
                className="flex-1 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-400 text-white font-semibold py-2.5 rounded-lg transition-colors"
              >
                {loading ? 'Saving…' : 'Save Patient'}
              </button>
              <Link
                to="/dashboard"
                className="flex-1 text-center border border-gray-300 hover:bg-gray-50 text-gray-700 font-semibold py-2.5 rounded-lg transition-colors"
              >
                Cancel
              </Link>
            </div>
          </form>
        </div>
      </main>
    </div>
  )
}
