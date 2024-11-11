import { useAuth } from '../context/AuthContext';
import React, { useEffect, useState } from 'react';
import { gtaApi } from '../misc/GtaApi'


const Home = () => {
  const { getUser } = useAuth();  // Access the getUser function from context

  const user = getUser();  // Get the user object
  
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const hasData = (data) => data && data.length > 0;


  useEffect(() => {
    if (user) {
      const token = localStorage.getItem("authToken");

      // Set up the config object inside useEffect
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,  // Add token to headers
        },
      };

      gtaApi.loadGTAApplicationInfo(user, config)  // Pass config to the API call
        .then(response => {
          setData(response.data);  // Set the data received from the request
          setLoading(false);
        })
        .catch(error => {
          console.error('Error fetching data:', error);
          setLoading(false);
        });
    }
  }, [user]);  // Only depend on 'user' for the effect to run

  // Render student records
  const renderStudentRecords = (studentRecords) => {
    if (!hasData(studentRecords)) {
      return <tr><td colSpan="4">No records available</td></tr>;
    }
    return studentRecords.map((record, index) => (
      <tr key={index}>
        <td>{record.cyseId}</td>
        <td>{record.semester}</td>
        <td>{record.year}</td>
        <td>{record.grade}</td>
      </tr>
    ));
  };

  // Render selected courses
  const renderSelectedCourses = (selectedCourses) => {
    if (!hasData(selectedCourses)) {
      return <tr><td colSpan="2">No courses selected</td></tr>;
    }
    return selectedCourses.map((course, index) => (
      <tr key={index}>
        <td>{course.courseID}</td>
        <td>{course.order}</td>
      </tr>
    ));
  };

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



  // If user data is available, display the name, else display a default message
  return (
    <div>
      {user && user.data ? (
        <h2>Welcome, {user.data.name}!</h2>  // Display the user's name
      ) : (
        <h2>Welcome, Guest!</h2>  // Fallback if no user is logged in
      )}
      <table border="1" style={{ width: '100%', textAlign: 'left' }}>
        <thead>
          <tr>
            <th>Username</th>
            <th>International Student</th>
            <th>Was GTA</th>
            <th>CYSE Student</th>
            <th>Application Submission</th>
            <th>CYSE Admin Office Decision</th>
            <th>CYSE Admin Comments</th>
            <th>CYSE Chair Decision</th>
            <th>CYSE Chair Comments</th>
            <th>Course Allocated</th>
            <th>Contract Signed</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{data.application_info.username || 'N/A'}</td>
            <td>{data.application_info.isInternationalStudent ? "Yes" : "No"}</td>
            <td>{data.application_info.wasGTA ? "Yes" : "No"}</td>
            <td>{data.application_info.isCYSEStudent ? "Yes" : "No"}</td>
            <td>{data.ApplicationSubmission || 'N/A'}</td>
            <td>{data.CYSEAdminOfficeDecision || 'N/A'}</td>
            <td>{data.CYSEAdminComments || 'N/A'}</td>
            <td>{data.CYSEChairDecision || 'N/A'}</td>
            <td>{data.CYSEChairComments || 'N/A'}</td>
            <td>{data.CourseAllocated || 'N/A'}</td>
            <td>{data.ContractSigned ? "Yes" : "No"}</td>
          </tr>
        </tbody>
      </table>

      <h4>Student Records</h4>
      <table border="1" style={{ width: '100%', textAlign: 'left' }}>
        <thead>
          <tr>
            <th>CYSE ID</th>
            <th>Semester</th>
            <th>Year</th>
            <th>Grade</th>
          </tr>
        </thead>
        <tbody>
          {renderStudentRecords(data.application_info.studentRecords)}
        </tbody>
      </table>

      <h4>Selected Courses</h4>
      <table border="1" style={{ width: '100%', textAlign: 'left' }}>
        <thead>
          <tr>
            <th>Course ID</th>
            <th>Order</th>
          </tr>
        </thead>
        <tbody>
          {renderSelectedCourses(data.application_info.selectedCourses)}
        </tbody>
      </table>

      <h4>GTA History Courses</h4>
      <table border="1" style={{ width: '100%', textAlign: 'left' }}>
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
    </div>
  );
};

export default Home;