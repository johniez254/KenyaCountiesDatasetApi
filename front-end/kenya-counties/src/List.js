import React, { useState } from 'react'
import { FaAngleDoubleRight, FaHotel } from 'react-icons/fa'

const List = ({ countiesData }) => {
  const [defaultCounty, setDefaultCounty] = useState(countiesData)
  const [value, setValue] = useState(0)
  const [isToggled, setIsToggled] = useState(false)
  const [id, setId] = useState(null)

  const getCounty = (id) => {
    setValue(id - 1)
  }

  const displayWard = (id) => {
    setIsToggled(!isToggled)
    setId(id)
  }

  const {
    countyName,
    countyCode,
    totalConstituencies,
    totalWards,
    constituencies,
  } = defaultCounty[value]
  return (
    <>
      <div className='jobs-center'>
        <div className='btn-container'>
          {countiesData.map((county, index) => {
            return (
              <button
                key={county.county_id}
                className={`job-btn ${index === value && 'active-btn'}`}
                onClick={() => getCounty(county.county_id)}
              >
                {county.countyName}
              </button>
            )
          })}
        </div>
        <article className='job-info'>
          <h3>{countyName}</h3>
          <h4>{countyCode}</h4>

          <h5>Constituencies {constituencies.length}</h5>
          {constituencies.map((constituency, index) => {
            const {
              constituency_id,
              constituencyName,
              constituencyCode,
              wards,
            } = constituency
            return (
              <div
                className='job-desc'
                key={constituency_id}
                onClick={() => displayWard(constituency_id)}
              >
                <FaAngleDoubleRight className='job-icon' />
                <p>
                  <span>({constituencyCode})</span> {constituencyName}
                </p>
                {wards.map((ward) => {
                  const { ward_id, wardName, wardCode, wardConstituencyId } =
                    ward

                  return (
                    <p className='wards' key={ward_id}>
                      {isToggled && wardConstituencyId === id ? wardName : ''}
                    </p>
                  )
                })}
              </div>
            )
          })}
        </article>
      </div>
    </>
  )
}

export default List
