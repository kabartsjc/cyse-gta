import React, { useState } from "react";

const StatusLegendModal = ({ getStatusIcon }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  // Function to open the modal
  const openModal = () => setIsModalOpen(true);

  // Function to close the modal
  const closeModal = () => setIsModalOpen(false);

  return (
    <div>
      {/* Trigger for the Modal */}
      <button onClick={openModal} className="legend-btn">
        View Status Legend
      </button>

      {/* Modal for Legend */}
      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button onClick={closeModal} className="close-modal">X</button>
            <h4>Status Legend</h4>
            <table className="styled-table">
              <thead>
                <tr>
                  <th>Status Icon</th>
                  <th>Status Meaning</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{getStatusIcon('inprocessing')}</td>
                  <td>In Processing</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('approved')}</td>
                  <td>Approved</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('deny')}</td>
                  <td>Deny</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('incompleted')}</td>
                  <td>Incomplete</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('notstarted')}</td>
                  <td>Not Started</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('nosubmited')}</td>
                  <td>Not Submitted</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('checked')}</td>
                  <td>Checked</td>
                </tr>
                <tr>
                  <td>{getStatusIcon('error')}</td>
                  <td>Error</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default StatusLegendModal;
