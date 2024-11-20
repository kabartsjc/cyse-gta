import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { gtaApi } from '../misc/GtaApi'
import { useNavigate } from "react-router-dom";



import { Container, Form, Button, Segment,  Dropdown } from 'semantic-ui-react';
import Swal from 'sweetalert2';


function ApplicationPage() {

  //const [wasGTACB, setWasGTACB] = useState(false);
  //const [gtaCourseHistory, setGtaCourseHistory] = useState([{ cyseId: '', semester: '', year: '' }]);


  const navigate = useNavigate();


  const Auth = useAuth();
  const user = Auth.getUser();
  const isUser = user.data.authorities === 'USER';

  const [cvFile, setCVFile] = useState(null);

  const [isInternationalStudentCB, setIsInternationalStudentCB] = useState(false);

  const [celtdCertFile, setCeltdCertFile] = useState(null);
  const [transcriptFile, setTranscriptFile] = useState(null);

  const [toeflScoreFile, setToeflScoreFile] = useState(null);

 /*  const semesterOptions = [
    { key: 'summer', text: 'SUMMER', value: 'SUMMER' },
    { key: 'spring', text: 'SPRING', value: 'SPRING' },
    { key: 'fall', text: 'FALL', value: 'FALL' },
  ];
 */
  /* const courseOptions = [
    { key: 'CYSE 101', text: 'CYSE 101: Introduction to Cyber Security Engineering', value: 'CYSE 101' },
    { key: 'CYSE 130', text: 'CYSE 130: Introduction to Computing for Digital Systems Engineering', value: 'CYSE 130' },
    { key: 'CYSE 211', text: 'CYSE 211: Operating Systems and Lab', value: 'CYSE 211' },
    { key: 'CYSE 230', text: 'CYSE 230: Computer Networking', value: 'CYSE 230' },
    { key: 'CYSE 304', text: 'CYSE 304: Cyber Security in Logic Design and Digital Systems', value: 'CYSE 304' },
    { key: 'CYSE 395', text: 'CYSE 395: Cyber Security Engineering Internship', value: 'CYSE 395' },
    { key: 'CYSE 411', text: 'CYSE 411: Secure Software Engineering', value: 'CYSE 411' },
    { key: 'CYSE 421', text: 'CYSE 421: Industrial Control Systems Security', value: 'CYSE 421' },
    { key: 'CYSE 424', text: 'CYSE 424: Embedded and Real Time Systems', value: 'CYSE 424' },
    { key: 'CYSE 425', text: 'CYSE 425: Secure RF Communications', value: 'CYSE 425' },
    { key: 'CYSE 430', text: 'CYSE 430: Critical Infrastructure Protection', value: 'CYSE 430' },
    { key: 'CYSE 445', text: 'CYSE 445: System Security and Resilience', value: 'CYSE 445' },
    { key: 'CYSE 450', text: 'CYSE 450: Cyber Vulnerability Lab', value: 'CYSE 450' },
    { key: 'CYSE 460', text: 'CYSE 460: Power Systems and Smart Grid Security', value: 'CYSE 460' },
    { key: 'CYSE 461', text: 'CYSE 461: Power Grid Security', value: 'CYSE 461' },
    { key: 'CYSE 462', text: 'CYSE 462: Mobile Devices and Network Security', value: 'CYSE 462' },
    { key: 'CYSE 465', text: 'CYSE 465: Transportation Systems Design', value: 'CYSE 465' },
    { key: 'CYSE 467', text: 'CYSE 467: GPS Security', value: 'CYSE 467' },
    { key: 'CYSE 470', text: 'CYSE 470: Human Factors and Cyber Security Engineering', value: 'CYSE 470' },
    { key: 'CYSE 476', text: 'CYSE 476: Cryptography Fundamentals', value: 'CYSE 476' },
    { key: 'CYSE 477', text: 'CYSE 477: Intrusion Detection', value: 'CYSE 477' },
    { key: 'CYSE 478', text: 'CYSE 478: Cyber Security Audit and Compliance', value: 'CYSE 478' },
    { key: 'CYSE 479', text: 'CYSE 479: Methods of User Authentication', value: 'CYSE 479' },
    { key: 'CYSE 480', text: 'CYSE 480: Reverse Software Engineering', value: 'CYSE 480' },
    { key: 'CYSE 491', text: 'CYSE 491: Engineering Senior Seminar', value: 'CYSE 491' },
    { key: 'CYSE 492', text: 'CYSE 492: Senior Advanced Design Project I', value: 'CYSE 492' },
    { key: 'CYSE 493', text: 'CYSE 493: Senior Advanced Design Project II', value: 'CYSE 493' },
    { key: 'CYSE 498', text: 'CYSE 498: Independent Study in Cyber Security Engineering', value: 'CYSE 498' },
    { key: 'CYSE 499', text: 'CYSE 499: Special Topics in Cyber Security Engineering', value: 'CYSE 499' },
    { key: 'CYSE 521', text: 'CYSE 521: Industrial Control Systems Security', value: 'CYSE 521' },
    { key: 'CYSE 550', text: 'CYSE 550: Cyber Security Engineering Fundamentals', value: 'CYSE 550' },
    { key: 'CYSE 570', text: 'CYSE 570: Fundamentals of Operating Systems', value: 'CYSE 570' },
    { key: 'CYSE 580', text: 'CYSE 580: Hardware and Cyber Physical Systems', value: 'CYSE 580' },
    { key: 'CYSE 587', text: 'CYSE 587: Cyber Security Systems Engineering', value: 'CYSE 587' },
    { key: 'CYSE 610', text: 'CYSE 610: Networks and Cyber Security', value: 'CYSE 610' },
    { key: 'CYSE 630', text: 'CYSE 630: Cyber Risk Analysis and Advanced Tools', value: 'CYSE 630' },
    { key: 'CYSE 640', text: 'CYSE 640: Wireless Network Security', value: 'CYSE 640' },
    { key: 'CYSE 650', text: 'CYSE 650: Topics in Cyber Security Engineering', value: 'CYSE 650' },
    { key: 'CYSE 670', text: 'CYSE 670: Secure Design of Connected and Automated Vehicles', value: 'CYSE 670' },
    { key: 'CYSE 680', text: 'CYSE 680: Advanced Manufacturing Automation Security', value: 'CYSE 680' },
    { key: 'CYSE 681', text: 'CYSE 681: Secure Energy Efficient Supply Chains', value: 'CYSE 681' },
    { key: 'CYSE 682', text: 'CYSE 682: Formal Methods for Cyber Physical Systems Security', value: 'CYSE 682' },
    { key: 'CYSE 683', text: 'CYSE 683: Reverse Engineering Industrial Automation', value: 'CYSE 683' },
    { key: 'CYSE 685', text: 'CYSE 685: Unmanned Aerial Systems Security', value: 'CYSE 685' },
    { key: 'CYSE 689', text: 'CYSE 689: Artificial Intelligence Methods for Cybersecurity', value: 'CYSE 689' },
    { key: 'CYSE 690', text: 'CYSE 690: Cyber Security Engineering Capstone Project', value: 'CYSE 690' },
    { key: 'CYSE 698', text: 'CYSE 698: Independent Study and Research', value: 'CYSE 698' },
    { key: 'CYSE 700', text: 'CYSE 700: Research Methodology and Pedagogy in Cyber Security Engineering', value: 'CYSE 700' },
    { key: 'CYSE 701', text: 'CYSE 701: Advanced Cyber Security Systems Engineering', value: 'CYSE 701' },
    { key: 'CYSE 720', text: 'CYSE 720: Security of Autonomous Systems', value: 'CYSE 720' },
    { key: 'CYSE 750', text: 'CYSE 750: Secure Software Development Lifecycle', value: 'CYSE 750' },
    { key: 'CYSE 799', text: 'CYSE 799: Special Topics in Cyber Security Engineering', value: 'CYSE 799' },
  ]; */



  const handleIntStdCheckboxChange = (e, { checked }) => {
    setIsInternationalStudentCB(checked);
  };


  /* const handleGTAHistCheckboxChange = (e, { checked }) => {
    setWasGTACB(checked);
  };
 */
  const handleFileChange = (e, setFileFunction) => {
    setFileFunction(e.target.files[0]);
  };

  

  /* const handleGtaHistoryChange = (index, field, value) => {
    const updatedHistory = [...gtaCourseHistory];
    updatedHistory[index][field] = value;
    setGtaCourseHistory(updatedHistory);
  }; */


 /*  const addGtaHistoryRow = () => {
    setGtaCourseHistory([...gtaCourseHistory, { cyseId: '', semester: '', year: '' }]);
  }; */


  //  const application = { username,cvFile, introGTAVideo,  isInternationalStudent, isInternationalStudent,wasGTA,studentHistoryCB,}


  const [appyear, setYear] = useState('');
  const [appperiod, setPeriod] = useState('');

  const [introGTAVideo, setIntroGTAVideo] = useState('');



  // Handle the year input change
  const handleYearChange = (e) => {
    setYear(e.target.value);
  };

  const handlePeriodChange = (e, { value }) => {
    setPeriod(value);
  };

  const handleVideoChange = (e, { value }) => {
    setIntroGTAVideo(value);
  };




  // Period dropdown options
  const periodOptions = [
    { key: 'fall', text: 'FALL', value: 'FALL' },
    { key: 'spring', text: 'SPRING', value: 'SPRING' },
    { key: 'summer', text: 'SUMMER', value: 'SUMMER' },
  ];


  useEffect(() => {
    // Show popup before the page is available
    Swal.fire({
      title: 'Welcome',
      text: 'Please carefully review all your application materials before submission. If you have previously submitted, any new submission will overwrite the previous one. You can check the status of your submitted materials on the home page.',
      icon: 'info',
      confirmButtonText: 'Proceed',
      allowOutsideClick: false, // Prevent clicking outside to close
      allowEscapeKey: false,   // Prevent using the escape key to close
    }).then(() => {
      console.log("Popup closed, user is viewing the page now.");
    });
  }, []);


  const handleSubmitApplication = async () => {


    try {

      const formData = new FormData();



      let form_error = false

      let form_error_descript = ""




      const user = Auth.getUser();
      console.log("username:", user.data.email); // Debug line


      formData.append('username', user.data.email)



        formData.append('cvFile', cvFile);
      

      // Create a JSON object for the application data
      const applicationData = {
        //wasGTACB: wasGTACB,
        appyear: appyear,
        appperiod: appperiod,
        introGTAVideo: introGTAVideo

      };

      if (appyear === "" || appperiod === "") {
        form_error = true
        form_error_descript += 'Your must provided the YEAR and the Period of the Application!\n';
      }


      // Only add student history if the student was a previous GTA
      /* if (wasGTACB) {

        if (gtaCourseHistory.length !== 0) {
          applicationData.gtaCourseHistory = gtaCourseHistory; // This contains previous course history with grades
        }
      }
 */
      // APPEND THE TRANSCRIPT
      formData.append('transcriptFile', transcriptFile);


      // Include additional files only for international students
      if (isInternationalStudentCB) {
        if (celtdCertFile) {
          formData.append('celtdCertFile', celtdCertFile);
        }
        if (toeflScoreFile) {
          formData.append('toeflScoreFile', toeflScoreFile);
        }
      }

      if (!form_error) {
        // Send the application data to the server

        formData.append('application_data', JSON.stringify(applicationData)); // Append application data


        const token = localStorage.getItem("authToken");
        console.log("Token:", token); // Debug line


        const config = {
          headers: {
            //"Content-Type": "application/json",
            // 'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${token}`,
          },
        };

        


        let response = await gtaApi.submitApplication(user, config, formData);

        console.log("Full Response:", response);



        // Check for 401 status to handle token renewal
        if (response?.status === 401) {
          await gtaApi.renewToken(); // Call your token renewal function here
          config.headers["Authorization"] = `Bearer ${localStorage.getItem("authToken")}`;

          response = await gtaApi.submitApplication(user, config, formData);
        }

        console.log("Response Status:", response.status);
        console.log("Response Data:", response.data);


        if (response?.status === 200) {
          Swal.fire({
            title: 'Success',
            text: 'Application submitted successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });

          if (response?.status === 200) {
            navigate("/home");
          }
        } else {
          Swal.fire({
            title: 'Unexpected Response',
            text: `The server returned an unexpected response: ${response?.status || 'No Status'}..`,
            icon: 'warning',
            confirmButtonText: 'OK'
          });
        }
      } else {
        Swal.fire({
          title: 'Missed Information',
          text: form_error_descript,
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }

    } catch (error) {

      // Log error and display an error alert
      console.error('Submission error:', error);
      Swal.fire({
        title: 'Submission Failed',
        text: 'There was an error submitting the application. Please try again later.',
        icon: 'error',
        confirmButtonText: 'OK'
      });
    }
  };



  // Create the selected courses based on grades A or B

  if (!isUser) {
    return <Navigate to='/' />;
  }

  return (
    <Container className='gmu-theme'>
      <Form>

        <Segment>
          <Form.Field>
            <label>Year</label>
            <Form.Input
              type="number"
              value={appyear}
              onChange={handleYearChange}
              placeholder="Enter the year"
            />
          </Form.Field>

          {/* Period Dropdown */}
          <Form.Field>
            <label>Semester</label>
            <Dropdown
              selection
              options={periodOptions}
              value={appperiod}
              onChange={handlePeriodChange}
              placeholder="Select Period"
            />
          </Form.Field>

          <Form.Field>
            <label>
              <div> <h3>Video Submission Guidelines:</h3>
                <ul>
                  <il> - Please prepare a short video of you teaching a technical course or lab (irrespective of whether or not you are interested in teaching a recitation or lab).<br /></il>
                  <il> - The video should be less than 3 minutes. <br /></il>
                  <il> - Please publish the video on your Youtube site.<br /></il>
                  <il> - You must submit the link of the video.<br /></il>

                  <br />
                  <il> - <b> Ensure your YouTube video is set to ‘Public’ and doesn’t require special permission.</b> <br /></il>
                  <il> - The application would also be incomplete if the interview committee could not play the video.<br /></il>
                  <il> -  Please double-check the video quality before you submit it. Video images should be clear and steady—no blurry, up-side-down videos, etc.<br /> </il>

                </ul>

              </div>

            </label>
            <Form.Input
              type="string"
              value={introGTAVideo}
              onChange={handleVideoChange}
              placeholder="Enter the Youtube Link of the video"
            />
          </Form.Field>


        </Segment>

        {/* Segment for File Uploads */}
        <Segment>
          <h4>File Uploads</h4>
          <Form.Input
            type='file'
            label='Submit your Curriculum-Vitae (pdf)'
            onChange={(e) => handleFileChange(e, setCVFile)}
          />


        </Segment>

        <Segment>
          <h4>International Student Forms</h4>

          <Form.Field>
            <Form.Checkbox
              label='Are you an international student?'
              checked={isInternationalStudentCB}
              onChange={handleIntStdCheckboxChange}
            />
          </Form.Field>
          {isInternationalStudentCB && (
            <>
              <Form.Input
                type='file'
                label='Submit your Classroom English Language and Teaching Demonstration (CELTD) (pdf)'
                disabled={!isInternationalStudentCB} // Enable/disable based on checkbox
                onChange={(e) => handleFileChange(e, setCeltdCertFile)}
              />
              <Form.Input
                type='file'
                label='Submit your TOEFL Score (pdf)'
                disabled={!isInternationalStudentCB} // Enable/disable based on checkbox
                onChange={(e) => handleFileChange(e, setToeflScoreFile)}
              />
            </>
          )}
        </Segment>

        {/* <Segment>
          <Form.Field>
            <Form.Checkbox
              label='Have you previously been a CYSE GTA?'
              checked={wasGTACB}
              onChange={handleGTAHistCheckboxChange}
            />
          </Form.Field>

          {wasGTACB && (
            <>
              <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>CYSE-ID</Table.HeaderCell>
                    <Table.HeaderCell>Semester</Table.HeaderCell>
                    <Table.HeaderCell>Year</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {gtaCourseHistory.map((entry, index) => (
                    <Table.Row key={index}>
                      <Table.Cell>
                        <Dropdown
                          selection
                          options={courseOptions}
                          value={entry.cyseId}
                          onChange={(e, { value }) => handleGtaHistoryChange(index, 'cyseId', value)}
                          placeholder='Select Course'
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Dropdown
                          selection
                          options={semesterOptions}
                          value={entry.semester}
                          onChange={(e, { value }) => handleGtaHistoryChange(index, 'semester', value)}
                          placeholder='Select Semester'
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Form.Input
                          value={entry.year}
                          onChange={(e) => handleGtaHistoryChange(index, 'year', e.target.value)}
                          placeholder='Enter Year'
                        />
                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>

              <Button onClick={addGtaHistoryRow} primary>Add Row</Button>
            </>
          )}
        </Segment>
 */}
        <Segment>
          <h4>Student Records</h4>
          <Form.Input
            type='file'
            label='You must submit your transcript in PDF format, updated from the school website or official records. The transcript should include both undergraduate and graduate classes and mist contain the following information: UNIVERSITY, COURSE NAME, YEAR, and GRADE (A, B, C, D, or F).'
            onChange={(e) => handleFileChange(e, setTranscriptFile)}
          />
        </Segment>



        <Button onClick={handleSubmitApplication} primary>
          Submit Application
        </Button>
      </Form>
    </Container>
  );
}

export default ApplicationPage
