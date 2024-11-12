import { useAuth } from '../context/AuthContext';
import React, { useEffect, useState } from 'react';
import { gtaApi } from '../misc/GtaApi'
import { FaSpinner, FaCheckCircle, FaTimesCircle, FaExclamationCircle, FaHourglassHalf, FaBan, FaSearch, FaExclamationTriangle } from "react-icons/fa";
import StatusLegendModal from './StatusLegendModal'; // Import the modal component



const Home = () => {
  const { getUser } = useAuth();  // Access the getUser function from context

  const user = getUser();  // Get the user object

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const hasData = (data) => data && data.length > 0;


  const [dataLoaded, setDataLoaded] = useState(false);

  const getStatusIcon = (status) => {
    switch (status.toLowerCase()) {
      case 'inprocessing':
        return <FaSpinner title="In Processing" style={{ color: '#ffc107' }} />;
      case 'approved':
        return <FaCheckCircle title="Approved" style={{ color: '#28a745' }} />;
      case 'deny':
        return <FaTimesCircle title="Deny" style={{ color: '#dc3545' }} />;
      case 'incompleted':
        return <FaExclamationTriangle title="Incomplete" style={{ color: '#fd7e14' }} />;
      case 'notstarted':
        return <FaHourglassHalf title="Not Started" style={{ color: '#6c757d' }} />;
      case 'nosubmited':
        return <FaBan title="Not Submitted" style={{ color: '#6c757d' }} />;
      case 'checked':
        return <FaSearch title="Checked" style={{ color: '#007bff' }} />;
      case 'error':
        return <FaExclamationCircle title="Error" style={{ color: '#dc3545' }} />;
      default:
        return <FaBan title="Unknown Status" style={{ color: '#6c757d' }} />;
    }
  };

  useEffect(() => {
    if (user && !dataLoaded) {
      const token = localStorage.getItem("authToken");

      if (!token) return;

      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };

      gtaApi
        .loadGTAApplicationInfo(user, config)
        .then(response => {
          setData(response.data);
          setLoading(false);
          setDataLoaded(true); // Set the flag to prevent future calls
        })
        .catch(error => {
          console.error("Error fetching data:", error);
          setLoading(false);
        });
    }
  }, [user, dataLoaded]);



  // Render GTA history courses
  const renderGTAHistoryCourses = (gtaHistoryCourses) => {
    if (!hasData(gtaHistoryCourses)) {
      return <tr><td colSpan="3">No GTA history courses</td></tr>;
    }
    return gtaHistoryCourses.map((course, index) => (
      <tr key={index}>
        <td>{course.cyseId}</td>
        <td>{course.semester}</td>
        <td>{course.year}</td>
      </tr>
    ));
  };

  if (loading) {
    return <p>Loading data...</p>;
  }

  if (!data || !data.application_info) {
    return <p>No application data available.</p>;
  }

  
  console.log(data.process_info.CELTDSTATUS)


  // If user data is available, display the name, else display a default message
  return (
    <div className="page-container">
      <div className="content-container">
        {user && user.data ? (
      <h2>Welcome, {user.data.name}!</h2>  // Display the user's name
    ) : (
      <h2>Welcome, Guest!</h2>  // Fallback if no user is logged in
    )}

    <h4>Application Information</h4>
    <table className="styled-table">
      <thead>
        <tr>
          <th>Username</th>
          <th>International Student</th>
          <th>Student was GTA</th>
          <th>Application Submission</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{data.application_info.username || 'N/A'}</td>
          <td>{data.application_info.isInternationalStudent ? "Yes" : "No"}</td>
          <td>{data.application_info.wasGTA ? "Yes" : "No"}</td>
          <td>{data.process_info.ApplicationSubmission || 'N/A'}</td>
        </tr>
      </tbody>
    </table>

    <h4>Process Information</h4>
    <table className="styled-table">
      <tbody>
        {/* Line 1 */}
        <tr>
          <th style={{ width: '20%' }}>CYSE Admin Office Decision</th>
          <td>{getStatusIcon(data.process_info.CYSEAdminOfficeDecision || 'nosubmited')}</td>
          <th style={{ width: '20%' }}>CYSE Admin Comments</th>
          <td 
            style={{ 
              width: '30%', 
              whiteSpace: 'pre-wrap', 
              wordBreak: 'break-word', 
              maxHeight: '4.5em',  // Limit height for 3 lines of text
              overflow: 'hidden',
              textOverflow: 'ellipsis'
            }}
            title={data.process_info.CYSEAdminComments || 'N/A'} // Tooltip for full text on hover
          >
            {data.process_info.CYSEAdminComments || 'N/A'}
          </td>
        </tr>

        {/* Line 2 */}
        <tr>
          <th>CYSE Chair Decision</th>
          <td>{getStatusIcon(data.process_info.CYSEChairDecision || 'nosubmited')}</td>
          <th>CYSE Chair Comments</th>
          <td>{data.process_info.CYSEChairComments || 'N/A'}</td>
        </tr>

        {/* Line 3 */}
        <tr>
          <th>Course Allocated</th>
          <td>{data.process_info.CourseAllocated || 'N/A'}</td>
          <th>Contract Signed</th>
          <td>{data.process_info.ContractSigned ? "Yes" : "No"}</td>
        </tr>

        {/* Line 4 */}
        <tr>
          <th>CELTD Status</th>
          <td>{getStatusIcon(data.process_info.CELTDSTATUS || 'notstarted')}</td>
          <th>CV Status</th>
          <td>{getStatusIcon(data.process_info.CVSTATUS || 'notstarted')}</td>
        </tr>

        <tr>
          <th>Transcript Status</th>
          <td>{getStatusIcon(data.process_info.TRASCRIPTSTATUS || 'notstarted')}</td>
          <th>TOEFL Status</th>
          <td>{getStatusIcon(data.process_info.TOEFLSTATUS || 'notstarted')}</td>
        </tr>

        <tr>
          <th>Video Status</th>
          <td>{getStatusIcon(data.process_info.VIDEOSTATUS || 'notstarted')}</td>
        </tr>

        {/* Line 5 */}
        <tr>
          <th>Last Update</th>
          <td colSpan="5">{data.process_info.LastUpdate || 'N/A'}</td>
        </tr>
      </tbody>
    </table>

    <h4>GTA History Courses</h4>
    <table className="styled-table">
      <thead>
        <tr>
          <th>CYSE ID</th>
          <th>Semester</th>
          <th>Year</th>
        </tr>
      </thead>
      <tbody>
        {renderGTAHistoryCourses(data.application_info.gtaHistoryCourses)}
      </tbody>
    </table>

      {/* Status Legend Modal */}
      <StatusLegendModal getStatusIcon={getStatusIcon} />

  </div>
  </div>
  
  );
};

export default Home;